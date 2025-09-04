package com.invoo.orchestrator.exposition;

import com.invoo.orchestrator.application.config.security.LogoutService;
import com.invoo.orchestrator.application.exceptions.ClientException;
import com.invoo.orchestrator.application.exceptions.GenericException;
import com.invoo.orchestrator.application.exceptions.UserAlreadyExists;
import com.invoo.orchestrator.application.exceptions.UserNotConnected;
import com.invoo.orchestrator.domaine.entity.dto.auth.AuthenticationRequest;
import com.invoo.orchestrator.domaine.entity.dto.auth.AuthenticationResponse;
import com.invoo.orchestrator.domaine.entity.dto.auth.ConfirmCodeResponse;
import com.invoo.orchestrator.domaine.entity.dto.auth.RegisterRequest;
import com.invoo.orchestrator.domaine.entity.dto.auth.ResetPassword;
import com.invoo.orchestrator.domaine.entity.dto.auth.ResetPasswordToken;
import com.invoo.orchestrator.domaine.service.AuthenticationService;
import com.invoo.orchestrator.exposition.api.AuthenticationController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private LogoutService logoutService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void registerShouldReturnResponseEntityWhenRequestIsValid() {
        RegisterRequest   request          = new RegisterRequest();
        ResponseEntity expectedResponse = ResponseEntity.ok().build();
        when(authenticationService.register(request)).thenReturn(expectedResponse);

        ResponseEntity<?> response = authenticationController.register(request);

        assertEquals(expectedResponse, response);
        verify(authenticationService).register(request);
    }

    @Test
    void authenticateShouldReturnAuthenticationResponseWhenRequestIsValid() {
        AuthenticationRequest request  = new AuthenticationRequest();
        HttpServletResponse    response         = mock(HttpServletResponse.class);
        AuthenticationResponse expectedResponse = new AuthenticationResponse();
        when(authenticationService.authenticate(request, response)).thenReturn(expectedResponse);

        ResponseEntity<AuthenticationResponse> actualResponse = authenticationController.authenticate(request, response);

        assertEquals(expectedResponse, actualResponse.getBody());
        verify(authenticationService).authenticate(request, response);
    }

    @Test
    void isConnectedShouldReturnTrueWhenUserIsConnected() {
        HttpServletRequest request = mock( HttpServletRequest.class);
        HttpServletResponse response = mock( HttpServletResponse.class);
        when(authenticationService.isConnected(request, response)).thenReturn(true);

        ResponseEntity<Boolean> actualResponse = authenticationController.isConnected(request, response);

        assertTrue(actualResponse.getBody());
        verify(authenticationService).isConnected(request, response);
    }

    @Test
    void resetPasswordShouldReturnTrueWhenRequestIsValid() {
        ResetPassword request = new ResetPassword("email@mail.com");
        when(authenticationService.resetPassword(request)).thenReturn(ResponseEntity.ok(true));

        ResponseEntity<Boolean> response = authenticationController.resetPassword(request);

        assertTrue(response.getBody());
        verify(authenticationService).resetPassword(request);
    }

    @Test
    void confirmAccountShouldReturnResponseEntityWhenTokenIsValid() {
        ConfirmCodeResponse confirmCodeResponse = new ConfirmCodeResponse("validToken");
        HttpServletResponse    response         = mock(HttpServletResponse.class);
        ResponseEntity expectedResponse = ResponseEntity.ok().build();
        when(authenticationService.confirmAccount("validToken", response)).thenReturn(expectedResponse );

        ResponseEntity<?> actualResponse = authenticationController.confirmAccount(confirmCodeResponse, response);

        assertEquals(expectedResponse, actualResponse);
        verify(authenticationService).confirmAccount("validToken", response);
    }

    @Test
    void isVerifiedAccountShouldReturnResponseEntityWhenEmailIsValid() {
        String email = "test@example.com";
        HttpServletResponse response = mock(HttpServletResponse.class);
        ResponseEntity expectedResponse = ResponseEntity.ok(true);
        when(authenticationService.isVerifiedAccount(email, response)).thenReturn(expectedResponse);

        ResponseEntity<?> actualResponse = authenticationController.isVerifiedAccount(email, response);

        assertEquals(expectedResponse, actualResponse);
        verify(authenticationService).isVerifiedAccount(email, response);
    }

    @Test
    void resetPasswordWithTokenShouldReturnTrueWhenTokenAndRequestAreValid() {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken("newPassword", "newPassword");
        String             token              = "validToken";
        when(authenticationService.resetPasswordToken(resetPasswordToken, token)).thenReturn(ResponseEntity.ok(true));

        ResponseEntity<Boolean> response = authenticationController.resetPasswordWithToken(resetPasswordToken, token);

        assertTrue(response.getBody());
        verify(authenticationService).resetPasswordToken(resetPasswordToken, token);
    }

    @Test
    void refreshTokenShouldCallServiceMethod() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        authenticationController.refreshToken(request, response);

        verify(authenticationService).refreshToken(request, response);
    }

    @Test
    void logoutShouldCallLogoutServiceAndReturnOkResponse() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response       = mock(HttpServletResponse.class);
        Authentication      authentication = mock(Authentication.class);

        ResponseEntity<?> responseEntity = authenticationController.logout(request, response, authentication);

        var expectedBody = Map.of("message", "Logout successful");

        assertEquals(expectedBody, responseEntity.getBody());
        verify(logoutService).logout(request, response, authentication);
    }

    @Test
    void registerShouldPropagateErrorWhenUserAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        when(authenticationService.register(request)).thenThrow(new UserAlreadyExists());

        assertThrows(UserAlreadyExists.class, () -> authenticationController.register(request));
        verify(authenticationService).register(request);
    }

    @Test
    void authenticateShouldPropagateErrorWhenCredentialsInvalid() {
        AuthenticationRequest request = new AuthenticationRequest();
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(authenticationService.authenticate(request, response)).thenThrow(new ClientException("Identifiants invalides"));

        assertThrows( ClientException.class, () -> authenticationController.authenticate(request, response));
        verify(authenticationService).authenticate(request, response);
    }

    @Test
    void isConnectedShouldReturnFalseWhenUserIsNotConnected() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(authenticationService.isConnected(request, response)).thenReturn(false);

        ResponseEntity<Boolean> actualResponse = authenticationController.isConnected(request, response);

        assertEquals(false, actualResponse.getBody());
        verify(authenticationService).isConnected(request, response);
    }

    @Test
    void confirmAccountShouldPropagateErrorWhenTokenInvalid() {
        ConfirmCodeResponse confirmCodeResponse = new ConfirmCodeResponse("invalidToken");
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(authenticationService.confirmAccount("invalidToken", response)).thenThrow(new GenericException("Token invalide", HttpStatus.BAD_REQUEST));

        assertThrows(GenericException.class, () -> authenticationController.confirmAccount(confirmCodeResponse, response));
        verify(authenticationService).confirmAccount("invalidToken", response);
    }

    @Test
    void resetPasswordWithTokenShouldPropagateErrorWhenTokenInvalid() {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken("password", "password");
        String token = "invalidToken";
        when(authenticationService.resetPasswordToken(resetPasswordToken, token)).thenThrow(new GenericException("Token invalide", HttpStatus.BAD_REQUEST));

        assertThrows(
                GenericException.class, () -> authenticationController.resetPasswordWithToken(resetPasswordToken, token));
        verify(authenticationService).resetPasswordToken(resetPasswordToken, token);
    }

    @Test
    void resetPasswordWithTokenShouldPropagateErrorWhenPasswordsDoNotMatch() {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken("password1", "password2");
        String token = "validToken";
        when(authenticationService.resetPasswordToken(resetPasswordToken, token)).thenThrow(new ClientException("Les mots de passe ne correspondent pas"));

        assertThrows(ClientException.class, () -> authenticationController.resetPasswordWithToken(resetPasswordToken, token));
        verify(authenticationService).resetPasswordToken(resetPasswordToken, token);
    }

    @Test
    void refreshTokenShouldPropagateIOExceptionWhenServiceFails() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        doThrow(new IOException("Erreur lors du rafraîchissement du token")).when(authenticationService).refreshToken(request, response);

        assertThrows(IOException.class, () -> authenticationController.refreshToken(request, response));
        verify(authenticationService).refreshToken(request, response);
    }

    @Test
    void isVerifiedAccountShouldPropagateErrorWhenEmailInvalid() {
        String email = "invalid@example.com";
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(authenticationService.isVerifiedAccount(email, response)).thenThrow(new ClientException("Email invalide"));

        assertThrows(ClientException.class, () -> authenticationController.isVerifiedAccount(email, response));
        verify(authenticationService).isVerifiedAccount(email, response);
    }

    @Test
    void resetPasswordShouldPropagateErrorWhenEmailNotFound() {
        ResetPassword request = new ResetPassword("nonexistent@mail.com");
        when(authenticationService.resetPassword(request)).thenThrow(new ClientException("Email non trouvé"));

        assertThrows(ClientException.class, () -> authenticationController.resetPassword(request));
        verify(authenticationService).resetPassword(request);
    }

    @Test
    void isConnectedShouldPropagateErrorWhenUserNotConnected() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(authenticationService.isConnected(request, response)).thenThrow(new UserNotConnected());

        assertThrows( UserNotConnected.class, () -> authenticationController.isConnected(request, response));
        verify(authenticationService).isConnected(request, response);
    }

    @Test
    void logoutShouldPropagateErrorWhenUserNotConnected() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);
        doThrow(new UserNotConnected()).when(logoutService).logout(request, response, authentication);

        assertThrows(UserNotConnected.class, () -> authenticationController.logout(request, response, authentication));
        verify(logoutService).logout(request, response, authentication);
    }

}
