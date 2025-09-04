package com.invoo.orchestrator.jwt;

import com.invoo.orchestrator.application.config.filter.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtServiceTest {

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final long JWT_EXPIRATION = 86400000; // 1 day
    private static final long REFRESH_EXPIRATION = 604800000; // 7 days
    private static final String TEST_USERNAME = "testuser";

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Set the private fields using ReflectionTestUtils
        ReflectionTestUtils.setField(jwtService, "secretKey", SECRET_KEY);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", JWT_EXPIRATION);
        ReflectionTestUtils.setField(jwtService, "refreshExpiration", REFRESH_EXPIRATION);
        
        when(userDetails.getUsername()).thenReturn(TEST_USERNAME);
    }

    @Test
    void extractUsername_ShouldReturnUsername() {
        // Arrange
        String token = createTestToken();
        
        // Act
        String username = jwtService.extractUsername(token);
        
        // Assert
        assertEquals(TEST_USERNAME, username);
    }

    @Test
    void extractClaim_ShouldReturnClaimValue() {
        // Arrange
        String token = createTestToken();
        
        // Act
        Date expirationDate = jwtService.extractClaim(token, Claims::getExpiration);
        
        // Assert
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    void generateToken_WithoutExtraClaims_ShouldCreateValidToken() {
        // Act
        String token = jwtService.generateToken(userDetails);
        
        // Assert
        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token, userDetails));
        assertEquals(TEST_USERNAME, jwtService.extractUsername(token));
    }

    @Test
    void generateToken_WithExtraClaims_ShouldCreateValidToken() {
        // Arrange
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "ADMIN");
        
        // Act
        String token = jwtService.generateToken(extraClaims, userDetails);
        
        // Assert
        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token, userDetails));
        assertEquals(TEST_USERNAME, jwtService.extractUsername(token));
        assertEquals("ADMIN", jwtService.extractClaim(token, claims -> claims.get("role")));
    }

    @Test
    void generateRefreshToken_ShouldCreateValidToken() {
        // Act
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        
        // Assert
        assertNotNull(refreshToken);
        assertTrue(jwtService.isTokenValid(refreshToken, userDetails));
        assertEquals(TEST_USERNAME, jwtService.extractUsername(refreshToken));
    }

    @Test
    void isTokenValid_WithValidToken_ShouldReturnTrue() {
        // Arrange
        String token = createTestToken();
        
        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);
        
        // Assert
        assertTrue(isValid);
    }

    @Test
    void isTokenValid_WithExpiredToken_ShouldReturnFalse() {
        // Arrange
        String expiredToken = createExpiredToken();

        // Act & Assert
        assertThrows(
                io.jsonwebtoken.ExpiredJwtException.class,
                () -> jwtService.isTokenValid(expiredToken, userDetails)
        );
    }

    @Test
    void isTokenValid_WithDifferentUsername_ShouldReturnFalse() {
        // Arrange
        String token = createTestToken();
        UserDetails differentUser = mock(UserDetails.class);
        when(differentUser.getUsername()).thenReturn("differentuser");
        
        // Act
        boolean isValid = jwtService.isTokenValid(token, differentUser);
        
        // Assert
        assertFalse(isValid);
    }

    // Helper methods to create test tokens
    private String createTestToken() {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(TEST_USERNAME)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String createExpiredToken() {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(TEST_USERNAME)
                .setIssuedAt(new Date(System.currentTimeMillis() - 2 * JWT_EXPIRATION))
                .setExpiration(new Date(System.currentTimeMillis() - JWT_EXPIRATION))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}