package com.invoo.orchestrator.application.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link UserAlreadyExists}
 * Tests the exception message handling
 */
class UserAlreadyExistsTest {

    /**
     * Test that the exception message is correctly set
     */
    @Test
    void constructor_ShouldSetMessageCorrectly() {
        // Act
        UserAlreadyExists exception = new UserAlreadyExists();
        
        // Assert
        assertEquals("User already exists", exception.getMessage());
    }
    
    /**
     * Test that the exception is a RuntimeException
     */
    @Test
    void exception_ShouldBeRuntimeException() {
        // Act
        UserAlreadyExists exception = new UserAlreadyExists();
        
        // Assert
        assertTrue(exception instanceof RuntimeException);
    }
}