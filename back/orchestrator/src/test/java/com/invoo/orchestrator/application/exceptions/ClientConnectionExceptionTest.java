package com.invoo.orchestrator.application.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link ClientConnectionException}
 * Tests the exception message formatting
 */
class ClientConnectionExceptionTest {

    /**
     * Test that the exception message is correctly formatted
     */
    @Test
    void constructor_ShouldFormatMessageCorrectly() {
        // Arrange
        String clientName = "test-client";
        String expectedMessage = "TEST-CLIENT service is not available";
        
        // Act
        ClientConnectionException exception = new ClientConnectionException(clientName);
        
        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }
    
    /**
     * Test that the exception is a RuntimeException
     */
    @Test
    void exception_ShouldBeRuntimeException() {
        // Arrange
        String clientName = "test-client";
        
        // Act
        ClientConnectionException exception = new ClientConnectionException(clientName);
        
        // Assert
        assertTrue(exception instanceof RuntimeException);
    }
    
    /**
     * Test with empty client name
     */
    @Test
    void constructor_WithEmptyClientName_ShouldHandleCorrectly() {
        // Arrange
        String clientName = "";
        String expectedMessage = " service is not available";
        
        // Act
        ClientConnectionException exception = new ClientConnectionException(clientName);
        
        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }
}