package com.invoo.orchestrator.application.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link ClientException}
 * Tests the exception message handling
 */
class ClientExceptionTest {

    /**
     * Test that the exception message is correctly set
     */
    @Test
    void constructor_ShouldSetMessageCorrectly() {
        // Arrange
        String clientName = "test-client";
        
        // Act
        ClientException exception = new ClientException(clientName);
        
        // Assert
        assertEquals(clientName, exception.getMessage());
    }
    
    /**
     * Test that the exception is a RuntimeException
     */
    @Test
    void exception_ShouldBeRuntimeException() {
        // Arrange
        String clientName = "test-client";
        
        // Act
        ClientException exception = new ClientException(clientName);
        
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
        
        // Act
        ClientException exception = new ClientException(clientName);
        
        // Assert
        assertEquals("", exception.getMessage());
    }
    
    /**
     * Test with null client name
     */
    @Test
    void constructor_WithNullClientName_ShouldHandleCorrectly() {
        // Arrange
        String clientName = null;
        
        // Act
        ClientException exception = new ClientException(clientName);
        
        // Assert
        assertEquals(null, exception.getMessage());
    }
}