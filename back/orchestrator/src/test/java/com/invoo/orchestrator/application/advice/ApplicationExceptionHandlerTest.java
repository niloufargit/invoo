package com.invoo.orchestrator.application.advice;

import com.invoo.orchestrator.application.exceptions.ClientConnectionException;
import com.invoo.orchestrator.application.exceptions.ClientException;
import com.invoo.orchestrator.application.exceptions.GenericException;
import com.invoo.orchestrator.application.exceptions.UserAlreadyExists;
import com.invoo.orchestrator.application.exceptions.UserNotConnected;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ApplicationExceptionHandlerTest {

    private ApplicationExceptionHandler handler;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new ApplicationExceptionHandler();
    }

    @Test
    void validationExceptionsReturnsBadRequestWithFieldErrors() {
        FieldError fieldError1 = new FieldError("object", "field1", "error message 1");
        FieldError fieldError2 = new FieldError("object", "field2", "error message 2");

        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError1, fieldError2));

        ResponseEntity<Map<String, String>> response = handler.handleValidationExceptions(methodArgumentNotValidException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errors = response.getBody();
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertEquals("error message 1", errors.get("field1"));
        assertEquals("error message 2", errors.get("field2"));
    }

    @Test
    void emptyValidationErrorsReturnsEmptyMap() {
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of());

        ResponseEntity<Map<String, String>> response = handler.handleValidationExceptions(methodArgumentNotValidException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errors = response.getBody();
        assertNotNull(errors);
        assertTrue(errors.isEmpty());
    }

    @Test
    void userNotConnectedReturnsUnauthorizedResponse() {
        UserNotConnected ex = new UserNotConnected();

        ResponseEntity<?> response = handler.handleUserNotConnected(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User not connected", response.getBody());
    }

    @Test
    void clientConnectionExceptionReturnsServiceUnavailableProblemDetail() {
        ClientConnectionException ex = new ClientConnectionException("Connection refused");

        ProblemDetail problemDetail = handler.handleException(ex);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), problemDetail.getStatus());
        assertEquals("Connection refused", problemDetail.getTitle());
        assertEquals("CONNECTION REFUSED service is not available", problemDetail.getDetail());
    }

    @Test
    void clientExceptionReturnsBadRequestProblemDetail() {
        ClientException ex = new ClientException("Bad request");

        ProblemDetail problemDetail = handler.handleException(ex);

        assertEquals(HttpStatus.BAD_REQUEST.value(), problemDetail.getStatus());
        assertEquals("Error when client processing", problemDetail.getTitle());
        assertEquals("Bad request", problemDetail.getDetail());
    }

    @Test
    void userAlreadyExistsReturnsUnauthorizedResponse() {
        UserAlreadyExists ex = new UserAlreadyExists();

        ResponseEntity<?> response = handler.handleUserNotConnected(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User already exists", response.getBody());
    }

    @Test
    void genericExceptionReturnsCustomHttpStatus() {
        GenericException ex = new GenericException("Generic error", HttpStatus.CONFLICT);

        ResponseEntity<?> response = handler.handleUserNotConnected(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Generic error", response.getBody());
    }

    @Test
    void disabledExceptionReturnsUnauthorizedResponse() {
        DisabledException ex = new DisabledException("User account is disabled");

        ResponseEntity<?> response = handler.handleUserNotConnected(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User account is disabled", response.getBody());
    }
}
