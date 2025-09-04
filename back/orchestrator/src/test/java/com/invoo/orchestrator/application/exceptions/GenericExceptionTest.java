package com.invoo.orchestrator.application.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link GenericException}
 * Tests the exception message and HTTP status handling
 */
class GenericExceptionTest {

    /**
     * Test that the exception message and HTTP status are correctly set
     */
    @Test
    void constructor_ShouldSetMessageAndStatusCorrectly() {
        // Arrange
        String message = "Test error message";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        // Act
        GenericException exception = new GenericException(message, status);
        
        // Assert
        assertEquals(message, exception.getMessage());
        assertEquals(status, exception.getHttpStatus());
    }
    
    /**
     * Test that the exception is a RuntimeException
     */
    @Test
    void exception_ShouldBeRuntimeException() {
        // Arrange
        String message = "Test error message";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        // Act
        GenericException exception = new GenericException(message, status);
        
        // Assert
        assertTrue(exception instanceof RuntimeException);
    }
    
    /**
     * Test with empty message
     */
    @Test
    void constructor_WithEmptyMessage_ShouldHandleCorrectly() {
        // Arrange
        String message = "";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        // Act
        GenericException exception = new GenericException(message, status);
        
        // Assert
        assertEquals("", exception.getMessage());
        assertEquals(status, exception.getHttpStatus());
    }
    
    /**
     * Test with null message
     */
    @Test
    void constructor_WithNullMessage_ShouldHandleCorrectly() {
        // Arrange
        String message = null;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        // Act
        GenericException exception = new GenericException(message, status);
        
        // Assert
        assertEquals(null, exception.getMessage());
        assertEquals(status, exception.getHttpStatus());
    }
    
    /**
     * Test with null HTTP status
     */
    @Test
    void constructor_WithNullHttpStatus_ShouldHandleCorrectly() {
        // Arrange
        String message = "Test error message";
        HttpStatus status = null;
        
        // Act
        GenericException exception = new GenericException(message, status);
        
        // Assert
        assertEquals(message, exception.getMessage());
        assertEquals(null, exception.getHttpStatus());
    }
}