package com.invoo.orchestrator.application.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link UserNotConnected}
 * Tests the exception message handling
 */
class UserNotConnectedTest {

    /**
     * Test that the exception message is correctly set
     */
    @Test
    void constructor_ShouldSetMessageCorrectly() {
        // Act
        UserNotConnected exception = new UserNotConnected();
        
        // Assert
        assertEquals("User not connected", exception.getMessage());
    }
    
    /**
     * Test that the exception is a RuntimeException
     */
    @Test
    void exception_ShouldBeRuntimeException() {
        // Act
        UserNotConnected exception = new UserNotConnected();
        
        // Assert
        assertTrue(exception instanceof RuntimeException);
    }
}