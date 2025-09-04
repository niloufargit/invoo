package com.invoo.orchestrator.domaine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invoo.global.confirmaccount.ConfirmAccount;
import com.invoo.global.notification.ResetPasswordEmail;
import com.invoo.orchestrator.application.config.filter.JwtService;
import com.invoo.orchestrator.application.exceptions.ApplicationExceptions;
import com.invoo.orchestrator.domaine.entity.Token;
import com.invoo.orchestrator.domaine.entity.User;
import com.invoo.orchestrator.domaine.entity.dto.auth.AuthenticationRequest;
import com.invoo.orchestrator.domaine.entity.dto.auth.AuthenticationResponse;
import com.invoo.orchestrator.domaine.entity.dto.auth.RegisterRequest;
import com.invoo.orchestrator.domaine.entity.dto.auth.ResetPassword;
import com.invoo.orchestrator.domaine.entity.dto.auth.ResetPasswordToken;
import com.invoo.orchestrator.domaine.entity.enums.TokenType;
import com.invoo.orchestrator.domaine.repository.ITokenRepository;
import com.invoo.orchestrator.domaine.repository.IUserRepository;
import com.invoo.orchestrator.infrastructure.notification.NotificationProducer;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${application.confirm-account.url}")
    private String confirmAccountUrl;

    @Value("${application.reset-password.url}")
    private String resetPasswordUrl;

    private final IUserRepository repository;
    private final ITokenRepository tokenJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final NotificationProducer notificationProducer;

    public ResponseEntity<?> register(RegisterRequest request) {

        if (!request.getConfirmPassword().equals( request.getPassword() )) {
            log.info( "Password and confirm password must be the same" );
            ApplicationExceptions.genericException(
                    "Password and confirm password must be the same",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (repository.existsByEmail( request.getEmail() )) {
            log.info( "User already exists with email : {}", request.getEmail() );
            ApplicationExceptions.UserAlreadyExists();
        }

        var user      = getUser( request );
        var savedUser = repository.save( user );
        var jwtToken  = jwtService.generateToken( user );
        saveUserToken( savedUser, jwtToken );

        log.info( "Sent notification to confirm account" );
        ConfirmAccount confirmAccount = new ConfirmAccount(
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                confirmAccountUrl + jwtToken
        );

        String message = stringMessage( confirmAccount );
        log.info( "Send notification to confirm account" );
        notificationProducer.confirmAccount( message );

        return new ResponseEntity<>( HttpStatus.OK );
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {

        try {
            log.info( "Authenticating user with email : {}", request.getEmail() );
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword() ) );
        } catch (Exception e) {
            log.info( "invalid credentials for requested email : {}", request.getEmail() );
            ApplicationExceptions.genericException( "Invalid credentials", HttpStatus.FORBIDDEN );
        }

        log.info( "Authentication : check if user email exist" );
        var user = repository.findByEmail( request.getEmail() ).orElseThrow();
        return getAuthenticationResponse( user, response );
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .userUuid( user.getId() )
                .token( jwtToken )
                .tokenType( TokenType.BEARER )
                .expired( false )
                .revoked( false )
                .build();
        tokenJpaRepository.save( token );
    }

    private void revokeAllUserTokens(User user) {
        // revoke all user tokens
        log.info( "revoke all user tokens" );
        var validUserTokens = tokenJpaRepository.findAllValidTokenByUser( user.getId() );
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach( token -> {
            token.setExpired( true );
            token.setRevoked( true );
        } );
        tokenJpaRepository.saveAll( validUserTokens );
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader( HttpHeaders.AUTHORIZATION );
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith( "Bearer " )) {
            return;
        }
        refreshToken = authHeader.substring( 7 );
        userEmail = jwtService.extractUsername( refreshToken );
        if (userEmail != null) {
            var user = this.repository.findByEmail( userEmail )
                    .orElseThrow();
            if (jwtService.isTokenValid( refreshToken, user )) {
                var accessToken = jwtService.generateToken( user );
                revokeAllUserTokens( user );
                saveUserToken( user, accessToken );
                // Set the JWT token in a cookie
                setCookie( response, accessToken );

                var authResponse = AuthenticationResponse.builder()
                        .accessToken( accessToken )
                        .refreshToken( refreshToken )
                        .build();
                new ObjectMapper().writeValue( response.getOutputStream(), authResponse );
            }
        }
    }

    public boolean isConnected(HttpServletRequest request, HttpServletResponse response) {

        log.info( "Check if user is connected" );
        if (request.getCookies() == null) return false;
        var jwt = Arrays.stream( request.getCookies() )
                .filter( cookie -> cookie.getName().equals( "jwt" ) )
                .map( Cookie::getValue ).findAny();

        return jwt.map( s -> tokenJpaRepository.findByToken( s )
                .map( t -> !t.isExpired() && !t.isRevoked() )
                .orElse( false ) ).orElse( false );

    }

    @Transactional
    public ResponseEntity<Boolean> resetPassword(ResetPassword request) {
        log.info( "Rest password" );
        var user = repository.findByEmail( request.email() );
        if (user.isEmpty()) {
            return ResponseEntity.status( 404 ).body( false );
        }

        revokeAllUserTokens( user.get() );
        var jwtToken = jwtService.generateToken( user.get() );
        saveUserToken( user.get(), jwtToken );
        // Sed an email to the user
        log.info( "Send notification to reset password" );
        var notification = new ResetPasswordEmail(
                user.get().getFirstname(),
                user.get().getLastname(),
                user.get().getEmail(),
                resetPasswordUrl + jwtToken );

        String message = stringMessage( notification );
        if (message == null) {
            return ResponseEntity.status( 500 ).body( false );
        }
        notificationProducer.resetPasswordNotification( message );

        return ResponseEntity.ok( true );
    }

    public ResponseEntity<Boolean> resetPasswordToken(ResetPasswordToken resetPasswordToken, String token) {

        if (token == null || token.isBlank()) {
            return ResponseEntity.status( 400 ).body( false );
        }

        var dbToken = tokenJpaRepository.findByToken( token );
        if (dbToken.isEmpty() || dbToken.get().isExpired() || dbToken.get().isRevoked()) {
            return ResponseEntity.status( 404 ).body( false );
        }


        if (!resetPasswordToken.password().equals( resetPasswordToken.confirmPassword() )) {
            return ResponseEntity.status( 400 ).body( false );
        }

        var userEmail = jwtService.extractUsername( token );
        if (userEmail == null) {
            return ResponseEntity.status( 400 ).body( false );
        }

        var user = repository.findByEmail( userEmail );
        if (user.isEmpty()) {
            return ResponseEntity.status( 404 ).body( false );
        }
        if (!jwtService.isTokenValid( token, user.get() )) {
            return ResponseEntity.status( 400 ).body( false );
        }

        tokenJpaRepository.killToken( token );

        user.get().setPassword( passwordEncoder.encode( resetPasswordToken.password() ) );
        repository.save( user.get() );
        return ResponseEntity.ok( true );

    }

    private <T> String stringMessage(T object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String       message;
        try {
            message = objectMapper.writeValueAsString( object );
        } catch (Exception e) {
            log.error( "Error parsing message: {}", e.getMessage() );
            return null;
        }
        return message;
    }


    public ResponseEntity<?> confirmAccount(String token, HttpServletResponse response) {

        log.info( "Confirm account with token" );
        var dbToken = tokenJpaRepository.findByToken( token );
        if (dbToken.isEmpty() || dbToken.get().isExpired() || dbToken.get().isRevoked()) {
            return ResponseEntity.status( 404 ).body( false );
        }

        var userEmail = jwtService.extractUsername( token );
        if (userEmail == null) {
            return ResponseEntity.status( 400 ).body( false );
        }

        var user = repository.findByEmail( userEmail );
        if (user.isEmpty()) {
            return ResponseEntity.status( 404 ).body( false );
        }

        if (!jwtService.isTokenValid( token, user.get() )) {
            return ResponseEntity.status( 400 ).body( false );
        }

        tokenJpaRepository.killToken( token );

        user.get().enableAccount();
        repository.save( user.get() );

        var body = getAuthenticationResponse( user.get(), response );

        return ResponseEntity.ok().body( body );
    }

    public ResponseEntity<?> isVerifiedAccount(String email, HttpServletResponse response) {
        log.info( "check if account is verified with email : {}", email );
        Boolean isVerified = repository.userIsVerified(email);

        if (isVerified == null) {
            return ResponseEntity.status(404).body(null);
        }

        var map = new HashMap<String, Object>();
        map.put("isVerified", isVerified);

        if (!isVerified) {
            log.info( "Email not yet confirmed" );
            return ResponseEntity.ok( map );
        }

        var user = repository.findByEmail(email).get();
        var res = getAuthenticationResponse( user, response );
        map.put("accessToken", res.getAccessToken());
        map.put("refreshToken", res.getRefreshToken());
        return ResponseEntity.ok(map);
    }

    private void setCookie(HttpServletResponse response, String jwt) {
        Cookie cookie = new Cookie( "jwt", jwt );
        cookie.setMaxAge( 7 * 24 * 60 * 60 ); // expires in 7 days
        cookie.setHttpOnly( true );
        cookie.setPath( "/" ); // Global
        response.addCookie( cookie );
    }

    private AuthenticationResponse getAuthenticationResponse(User user, HttpServletResponse response) {
        var jwtToken     = jwtService.generateToken( user );
        var refreshToken = jwtService.generateRefreshToken( user );
        revokeAllUserTokens( user );
        saveUserToken( user, jwtToken );
        //Set the JWT token in a cookie
        setCookie( response, jwtToken );
        repository.updateLastLogin( user.getId(), LocalDateTime.now() );
        return AuthenticationResponse.builder()
                .accessToken( jwtToken )
                .refreshToken( refreshToken )
                .build();
    }

    private User getUser(RegisterRequest request) {
        return User.builder()
                .firstname( request.getFirstname() )
                .lastname( request.getLastname() )
                .email( request.getEmail() )
                .password( passwordEncoder.encode( request.getPassword() ) )
                .createdAt( LocalDateTime.now() )
                .build();
    }

}
