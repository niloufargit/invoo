package com.invoo.orchestrator.domaine.service;

import com.invoo.orchestrator.application.config.filter.JwtService;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private IUserRepository repository;

    @Mock
    private ITokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private NotificationProducer notificationProducer;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AuthenticationService authenticationService;

    private User testUser;
    private Token testToken;
    private String jwtToken;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(authenticationService, "confirmAccountUrl", "http://localhost:8080/confirm/");

        testUser = User.builder()
                .id(UUID.randomUUID())
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("encodedPassword")
                .build();

        jwtToken = "jwt.token.here";

        testToken = Token.builder()
                .userUuid(testUser.getId())
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
    }

    @Test
    void enregistrementUtilisateurAvecSucces() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password");
        request.setConfirmPassword("password");

        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(repository.save(any(User.class))).thenReturn(testUser);
        when(jwtService.generateToken(any(User.class))).thenReturn(jwtToken);

        ResponseEntity<?> response = authenticationService.register(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(repository).existsByEmail("john.doe@example.com");
        verify(repository).save(any(User.class));
        verify(tokenRepository).save(any(Token.class));
        verify(notificationProducer).confirmAccount(anyString());
    }

    @Test
    void echecEnregistrementMotsDePasseDifferents() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password1");
        request.setConfirmPassword("password2");

        assertThrows(RuntimeException.class, () -> authenticationService.register(request));
        verify(repository, never()).save(any(User.class));
    }

    @Test
    void echecEnregistrementUtilisateurExistant() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password");
        request.setConfirmPassword("password");

        when(repository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authenticationService.register(request));
        verify(repository, never()).save(any(User.class));
    }

    @Test
    void authentificationReussie() {
        AuthenticationRequest request = new AuthenticationRequest("john.doe@example.com", "password");

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(jwtService.generateToken(any(User.class))).thenReturn(jwtToken);
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        AuthenticationResponse authResponse = authenticationService.authenticate(request, response);

        assertNotNull(authResponse);
        assertEquals(jwtToken, authResponse.getAccessToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(repository).updateLastLogin(eq(testUser.getId()), any(LocalDateTime.class));
    }

    @Test
    void confirmationCompteReussie() {
        when(tokenRepository.findByToken(jwtToken)).thenReturn(Optional.of(testToken));
        when(jwtService.extractUsername(jwtToken)).thenReturn("john.doe@example.com");
        when(repository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(testUser));
        when(jwtService.isTokenValid(jwtToken, testUser)).thenReturn(true);
        when(jwtService.generateToken(testUser)).thenReturn(jwtToken);
        when(jwtService.generateRefreshToken(testUser)).thenReturn("refreshToken");

        ResponseEntity<?> result = authenticationService.confirmAccount(jwtToken, response);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(tokenRepository).killToken(jwtToken);
        verify(repository).save(testUser);
        assertTrue(testUser.isEnabled());
    }

    @Test
    void echecConfirmationCompteTokenExpire() {
        testToken.setExpired(true);
        when(tokenRepository.findByToken(jwtToken)).thenReturn(Optional.of(testToken));

        ResponseEntity<?> result = authenticationService.confirmAccount(jwtToken, response);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(tokenRepository, never()).killToken(anyString());
    }

    @Test
    void verificationCompteUtilisateurVerifie() {
        when(repository.userIsVerified("john.doe@example.com")).thenReturn(true);
        when(repository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(testUser));
        when(jwtService.generateToken(testUser)).thenReturn(jwtToken);
        when(jwtService.generateRefreshToken(testUser)).thenReturn("refreshToken");

        ResponseEntity<?> result = authenticationService.isVerifiedAccount("john.doe@example.com", response);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        @SuppressWarnings("unchecked")
        HashMap<String, Object> body = (HashMap<String, Object>) result.getBody();
        assertNotNull(body);
        assertTrue((Boolean) body.get("isVerified"));
        assertEquals(jwtToken, body.get("accessToken"));
    }

    @Test
    void verificationCompteUtilisateurNonVerifie() {
        when(repository.userIsVerified("john.doe@example.com")).thenReturn(false);

        ResponseEntity<?> result = authenticationService.isVerifiedAccount("john.doe@example.com", response);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        @SuppressWarnings("unchecked")
        HashMap<String, Object> body = (HashMap<String, Object>) result.getBody();
        assertNotNull(body);
        assertFalse((Boolean) body.get("isVerified"));
        assertNull(body.get("accessToken"));
    }

    @Test
    void demandeReinitialisationMotDePasseReussie() {
        ResetPassword resetRequest = new ResetPassword("john.doe@example.com");

        when(repository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(testUser));
        when(jwtService.generateToken(testUser)).thenReturn(jwtToken);

        ResponseEntity<Boolean> result = authenticationService.resetPassword(resetRequest);

        assertTrue(result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(notificationProducer).resetPasswordNotification(anyString());
    }

    @Test
    void utilisateurConnecteQuandCookieValide() {
        Cookie[] cookies = new Cookie[] { new Cookie("jwt", jwtToken) };
        when(request.getCookies()).thenReturn(cookies);
        when(tokenRepository.findByToken(jwtToken)).thenReturn(Optional.of(testToken));

        boolean result = authenticationService.isConnected(request, response);

        assertTrue(result);
    }

    @Test
    void utilisateurNonConnecteQuandPasDeCookies() {
        when(request.getCookies()).thenReturn(null);

        boolean result = authenticationService.isConnected(request, response);

        assertFalse(result);
    }

    @Test
    void reinitialisationMotDePasseParTokenReussie() {
        ResetPasswordToken resetToken = new ResetPasswordToken("newPassword", "newPassword");

        when(tokenRepository.findByToken(jwtToken)).thenReturn(Optional.of(testToken));
        when(jwtService.extractUsername(jwtToken)).thenReturn("john.doe@example.com");
        when(repository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(testUser));
        when(jwtService.isTokenValid(jwtToken, testUser)).thenReturn(true);

        ResponseEntity<Boolean> result = authenticationService.resetPasswordToken(resetToken, jwtToken);

        assertTrue(result.getBody());
        verify(tokenRepository).killToken(jwtToken);
        verify(passwordEncoder).encode("newPassword");
        verify(repository).save(testUser);
    }

    @Test
    void echecReinitialisationMotDePasseMotsDePasseDifferents() {
        ResetPasswordToken resetToken = new ResetPasswordToken("password1", "password2");

        ResponseEntity<Boolean> result = authenticationService.resetPasswordToken(resetToken, jwtToken);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertFalse(result.getBody());
    }
}
