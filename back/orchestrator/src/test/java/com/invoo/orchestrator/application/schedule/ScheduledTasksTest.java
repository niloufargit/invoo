package com.invoo.orchestrator.application.schedule;

import com.invoo.orchestrator.domaine.entity.Token;
import com.invoo.orchestrator.domaine.entity.enums.TokenType;
import com.invoo.orchestrator.domaine.repository.ITokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link ScheduledTasks}
 * Tests the scheduled tasks that clean up expired and revoked tokens
 */
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.datasource.driver-class-name=org.h2.Driver"
})
class ScheduledTasksTest {

    @Mock
    private ITokenRepository tokenRepository;

    @InjectMocks
    private ScheduledTasks scheduledTasks;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test that the cleanDbToken method calls the repository method
     * to delete expired and revoked tokens
     */
    @Test
    void cleanDbToken_ShouldCallRepositoryDeleteMethod() {
        // Act
        scheduledTasks.cleanDbToken();

        // Assert
        verify(tokenRepository, times(1)).deleteTokenWhenExpiredIsTrueAndRevokedIsTrue();
    }

    /**
     * Test that simulates database insertion and deletion
     * This test verifies that only tokens that are both expired and revoked are deleted
     */
    @Test
    void cleanDbToken_ShouldDeleteOnlyExpiredAndRevokedTokens() {
        // Arrange
        // Create tokens with different combinations of expired and revoked flags
        Token validToken = createToken(false, false);
        Token expiredToken = createToken(true, false);
        Token revokedToken = createToken(false, true);
        Token expiredAndRevokedToken = createToken(true, true);

        // Create a list to simulate the database
        List<Token> tokensInDatabase = new ArrayList<>(Arrays.asList(
            validToken, expiredToken, revokedToken, expiredAndRevokedToken
        ));

        // Mock the repository to simulate database operations
        // When findTokensExpiredAndRevoked is called, return tokens that are expired or revoked
        when(tokenRepository.findTokensExpiredAndRevoked()).thenReturn(
            tokensInDatabase.stream()
                .filter(token -> token.isExpired() || token.isRevoked())
                .toList()
        );

        // When findByToken is called, return the token if it exists in the database
        when(tokenRepository.findByToken(any())).thenAnswer(invocation -> {
            String tokenValue = invocation.getArgument(0);
            return tokensInDatabase.stream()
                .filter(token -> token.getToken().equals(tokenValue))
                .findFirst();
        });

        // When deleteTokenWhenExpiredIsTrueAndRevokedIsTrue is called, remove tokens that are both expired and revoked
        doAnswer(invocation -> {
            tokensInDatabase.removeIf(token -> token.isExpired() && token.isRevoked());
            return null;
        }).when(tokenRepository).deleteTokenWhenExpiredIsTrueAndRevokedIsTrue();

        // Act
        // Call the method that should delete expired and revoked tokens
        scheduledTasks.cleanDbToken();

        // Assert
        // Verify that the repository method was called
        verify(tokenRepository, times(1)).deleteTokenWhenExpiredIsTrueAndRevokedIsTrue();

        // Verify that only the expired and revoked token was deleted
        assertEquals(3, tokensInDatabase.size());
        assertEquals(0, tokensInDatabase.stream()
            .filter(token -> token.isExpired() && token.isRevoked())
            .count());

        // Verify that the other tokens still exist
        assertEquals(1, tokensInDatabase.stream()
            .filter(token -> !token.isExpired() && !token.isRevoked())
            .count());
        assertEquals(1, tokensInDatabase.stream()
            .filter(token -> token.isExpired() && !token.isRevoked())
            .count());
        assertEquals(1, tokensInDatabase.stream()
            .filter(token -> !token.isExpired() && token.isRevoked())
            .count());
    }

    /**
     * Helper method to create a token with the specified expired and revoked flags
     */
    private Token createToken(boolean expired, boolean revoked) {
        Token token = new Token();
        token.setId(UUID.randomUUID());
        token.setToken(UUID.randomUUID().toString());
        token.setTokenType(TokenType.BEARER);
        token.setExpired(expired);
        token.setRevoked(revoked);
        token.setUserUuid(UUID.randomUUID());
        return token;
    }
}
