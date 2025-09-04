package com.invoo.orchestrator.infrastructure;

import com.invoo.orchestrator.domaine.entity.Token;
import com.invoo.orchestrator.infrastructure.repository.TokenJpaRepository;
import com.invoo.orchestrator.infrastructure.repository.impl.TokenRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenRepositoryImplTest {

    @Mock
    private TokenJpaRepository tokenJpaRepository;

    private TokenRepositoryImpl tokenRepository;

    @BeforeEach
    public void setUp() {
        tokenRepository = new TokenRepositoryImpl(tokenJpaRepository);
    }

    @Test
    public void findAllValidTokenByUser_ShouldReturnTokensFromRepository() {
        // Arrange
        UUID userId = UUID.randomUUID();
        List<Token> expectedTokens = Arrays.asList(new Token(), new Token());
        when(tokenJpaRepository.findAllValidTokenByUser(userId)).thenReturn(expectedTokens);

        // Act
        List<Token> result = tokenRepository.findAllValidTokenByUser(userId);

        // Assert
        assertEquals(expectedTokens, result);
        verify(tokenJpaRepository).findAllValidTokenByUser(userId);
    }

    @Test
    public void findTokensExpiredAndRevoked_ShouldReturnTokensFromRepository() {
        // Arrange
        List<Token> expectedTokens = Arrays.asList(new Token(), new Token());
        when(tokenJpaRepository.findTokensExpiredAndRevoked()).thenReturn(expectedTokens);

        // Act
        List<Token> result = tokenRepository.findTokensExpiredAndRevoked();

        // Assert
        assertEquals(expectedTokens, result);
        verify(tokenJpaRepository).findTokensExpiredAndRevoked();
    }

    @Test
    public void findByToken_WithExistingToken_ShouldReturnToken() {
        // Arrange
        String tokenValue = "valid-token";
        Token expectedToken = new Token();
        when(tokenJpaRepository.findByToken(tokenValue)).thenReturn(Optional.of(expectedToken));

        // Act
        Optional<Token> result = tokenRepository.findByToken(tokenValue);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedToken, result.get());
        verify(tokenJpaRepository).findByToken(tokenValue);
    }

    @Test
    public void findByToken_WithNonExistingToken_ShouldReturnEmptyOptional() {
        // Arrange
        String tokenValue = "invalid-token";
        when(tokenJpaRepository.findByToken(tokenValue)).thenReturn(Optional.empty());

        // Act
        Optional<Token> result = tokenRepository.findByToken(tokenValue);

        // Assert
        assertTrue(result.isEmpty());
        verify(tokenJpaRepository).findByToken(tokenValue);
    }

    @Test
    public void killToken_ShouldCallRepositoryMethod() {
        // Arrange
        String tokenValue = "token-to-kill";

        // Act
        tokenRepository.killToken(tokenValue);

        // Assert
        verify(tokenJpaRepository).killToken(tokenValue);
    }

    @Test
    public void deleteTokenWhenExpiredIsTrueAndRevokedIsTrue_ShouldCallRepositoryMethod() {
        // Arrange

        // Act
        tokenRepository.deleteTokenWhenExpiredIsTrueAndRevokedIsTrue();

        // Assert
        verify(tokenJpaRepository).deleteTokenWhenExpiredIsTrueAndRevokedIsTrue();
    }

    @Test
    public void save_ShouldCallRepositoryMethod() {
        // Arrange
        Token token = new Token();

        // Act
        tokenRepository.save(token);

        // Assert
        verify(tokenJpaRepository).save(token);
    }

    @Test
    public void saveAll_ShouldCallRepositoryMethod() {
        // Arrange
        List<Token> tokens = Arrays.asList(new Token(), new Token());

        // Act
        tokenRepository.saveAll(tokens);

        // Assert
        verify(tokenJpaRepository).saveAll(tokens);
    }

    @Test
    public void findTokensExpiredAndRevoked_Performance_ShouldCompleteWithinTimeLimit() {
        when(tokenJpaRepository.findTokensExpiredAndRevoked()).thenReturn(Arrays.asList(new Token(), new Token()));

        assertTimeout( Duration.ofMillis(500), () -> {
            tokenRepository.findTokensExpiredAndRevoked();
        });

        verify(tokenJpaRepository).findTokensExpiredAndRevoked();
    }

    @Test
    public void saveAll_Performance_ShouldCompleteWithinTimeLimit() {
        List<Token> tokens = Arrays.asList(new Token(), new Token());

        assertTimeout(Duration.ofMillis(500), () -> {
            tokenRepository.saveAll(tokens);
        });

        verify(tokenJpaRepository).saveAll(tokens);
    }


    @Test
    public void findAllValidTokenByUser_ConcurrentAccess_ShouldHandleConcurrentRequests() throws InterruptedException {
        UUID userId = UUID.randomUUID();
        List<Token> tokens = Arrays.asList(new Token(), new Token());
        when(tokenJpaRepository.findAllValidTokenByUser(userId)).thenReturn(tokens);

        Runnable task = () -> tokenRepository.findAllValidTokenByUser(userId);

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        verify(tokenJpaRepository, times(2)).findAllValidTokenByUser(userId);
    }

    @Test
    public void findAllValidTokenByUser_WithUnauthorizedAccess_ShouldThrowSecurityException() {
        UUID userId = UUID.randomUUID();
        when(tokenJpaRepository.findAllValidTokenByUser(userId)).thenThrow(new SecurityException("Unauthorized access"));

        Exception exception = assertThrows(SecurityException.class, () -> {
            tokenRepository.findAllValidTokenByUser(userId);
        });

        assertEquals("Unauthorized access", exception.getMessage());
    }

    @Test
    public void findByToken_WithUnauthorizedAccess_ShouldThrowSecurityException() {
        String tokenValue = "secure-token";
        when(tokenJpaRepository.findByToken(tokenValue)).thenThrow(new SecurityException("Unauthorized access"));

        Exception exception = assertThrows(SecurityException.class, () -> {
            tokenRepository.findByToken(tokenValue);
        });

        assertEquals("Unauthorized access", exception.getMessage());
    }

    @Test
    public void save_WithUnauthorizedAccess_ShouldThrowSecurityException() {
        Token token = new Token();
        doThrow(new SecurityException("Unauthorized access")).when(tokenJpaRepository).save(token);

        Exception exception = assertThrows(SecurityException.class, () -> {
            tokenRepository.save(token);
        });

        assertEquals("Unauthorized access", exception.getMessage());
    }

    @Test
    public void deleteTokenWhenExpiredIsTrueAndRevokedIsTrue_WithUnauthorizedAccess_ShouldThrowSecurityException() {
        doThrow(new SecurityException("Unauthorized access")).when(tokenJpaRepository).deleteTokenWhenExpiredIsTrueAndRevokedIsTrue();

        Exception exception = assertThrows(SecurityException.class, () -> {
            tokenRepository.deleteTokenWhenExpiredIsTrueAndRevokedIsTrue();
        });

        assertEquals("Unauthorized access", exception.getMessage());
    }

}