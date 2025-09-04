package com.invoo.orchestrator.application.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ApplicationExceptions}
 * Tests the static methods that create and throw exceptions
 */
class ApplicationExceptionsTest {

    /**
     * Test that connectionException returns a Mono that errors with ClientConnectionException
     */
    @Test
    void connectionException_ShouldReturnMonoWithClientConnectionException() {
        // Arrange
        String clientName = "test-client";
        
        // Act
        Mono<Object> result = ApplicationExceptions.connectionException(clientName);
        
        // Assert
        StepVerifier.create(result)
                .expectError(ClientConnectionException.class)
                .verify();
    }
    
    /**
     * Test that clientException returns a ClientException
     */
    @Test
    void clientException_ShouldReturnClientException() {
        // Arrange
        String clientName = "test-client";
        
        // Act
        Throwable exception = ApplicationExceptions.clientException(clientName);
        
        // Assert
        assertTrue(exception instanceof ClientException);
        assertEquals(clientName, exception.getMessage());
    }
    
    /**
     * Test that userNotConnected throws UserNotConnected exception
     */
    @Test
    void userNotConnected_ShouldThrowUserNotConnectedException() {
        // Assert
        assertThrows(UserNotConnected.class, () -> {
            // Act
            ApplicationExceptions.userNotConnected();
        });
    }
    
    /**
     * Test that UserAlreadyExists throws UserAlreadyExists exception
     */
    @Test
    void userAlreadyExists_ShouldThrowUserAlreadyExistsException() {
        // Assert
        assertThrows(UserAlreadyExists.class, () -> {
            // Act
            ApplicationExceptions.UserAlreadyExists();
        });
    }
    
    /**
     * Test that genericException throws GenericException with correct message and status
     */
    @Test
    void genericException_ShouldThrowGenericExceptionWithMessageAndStatus() {
        // Arrange
        String message = "Test error message";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        // Act & Assert
        GenericException exception = assertThrows(GenericException.class, () -> {
            ApplicationExceptions.genericException(message, status);
        });
        
        // Additional assertions
        assertEquals(message, exception.getMessage());
        assertEquals(status, exception.getHttpStatus());
    }
}