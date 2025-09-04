package com.invoo.orchestrator.domaine.repository;

import com.invoo.orchestrator.config.TestConfig;
import com.invoo.orchestrator.domaine.entity.User;
import com.invoo.orchestrator.infrastructure.repository.UserJpaRepository;
import com.invoo.orchestrator.infrastructure.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for IUserRepository
 * Tests all methods in the IUserRepository interface
 */
@SpringBootTest(classes = {TestConfig.class})
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.datasource.driver-class-name=org.h2.Driver"
})
public class IUserRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    private IUserRepository userRepository;

    private User testUser;
    private final String TEST_EMAIL = "test@example.com";
    private final String TEST_PASSWORD = "password";
    private final String TEST_FIRSTNAME = "Test";
    private final String TEST_LASTNAME = "User";

    @BeforeEach
    public void setUp() {
        // Clear the database before each test
        userJpaRepository.deleteAll();

        // Create the repository implementation
        userRepository = new UserRepositoryImpl(userJpaRepository);

        // Create a test user
        testUser = User.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .firstname(TEST_FIRSTNAME)
                .lastname(TEST_LASTNAME)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .build();

        // Save the test user
        testUser = userJpaRepository.save(testUser);
    }

    @Test
    public void testFindByEmail_ExistingEmail_ShouldReturnUser() {
        // Act
        Optional<User> foundUser = userRepository.findByEmail(TEST_EMAIL);

        // Assert
        assertTrue(foundUser.isPresent(), "User should be found by email");
        assertEquals(TEST_EMAIL, foundUser.get().getEmail(), "Email should match");
        assertEquals(TEST_FIRSTNAME, foundUser.get().getFirstname(), "First name should match");
        assertEquals(TEST_LASTNAME, foundUser.get().getLastname(), "Last name should match");
    }

    @Test
    public void testFindByEmail_NonExistingEmail_ShouldReturnEmpty() {
        // Act
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        // Assert
        assertFalse(foundUser.isPresent(), "User should not be found for non-existent email");
    }

    @Test
    public void testGetUserId_ExistingUsername_ShouldReturnId() {
        // Act
        UUID userId = userRepository.getUserId(TEST_EMAIL);

        // Assert
        assertEquals(testUser.getId(), userId, "User ID should match");
    }

    @Test
    public void testExistsByEmail_ExistingEmail_ShouldReturnTrue() {
        // Act
        boolean exists = userRepository.existsByEmail(TEST_EMAIL);

        // Assert
        assertTrue(exists, "User should exist by email");
    }

    @Test
    public void testExistsByEmail_NonExistingEmail_ShouldReturnFalse() {
        // Act
        boolean exists = userRepository.existsByEmail("nonexistent@example.com");

        // Assert
        assertFalse(exists, "User should not exist for non-existent email");
    }

    @Test
    public void testUserIsVerified_VerifiedUser_ShouldReturnTrue() {
        // Act
        Boolean isVerified = userRepository.userIsVerified(TEST_EMAIL);

        // Assert
        assertTrue(isVerified, "User should be verified");
    }

    @Test
    public void testUserIsVerified_NonVerifiedUser_ShouldReturnFalse() {
        // Arrange
        User nonVerifiedUser = User.builder()
                .email("nonverified@example.com")
                .password(TEST_PASSWORD)
                .firstname(TEST_FIRSTNAME)
                .lastname(TEST_LASTNAME)
                .enabled(false)
                .createdAt(LocalDateTime.now())
                .build();
        userJpaRepository.save(nonVerifiedUser);

        // Act
        Boolean isVerified = userRepository.userIsVerified("nonverified@example.com");

        // Assert
        assertFalse(isVerified, "User should not be verified");
    }

    @Test
    public void testUpdateLastLogin_ShouldUpdateLastLoginTime() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        userRepository.updateLastLogin(testUser.getId(), now);

        // Assert
        Optional<User> updatedUser = userRepository.findById(testUser.getId());
        assertTrue(updatedUser.isPresent(), "User should be found by ID");
        assertNotNull(updatedUser.get().getLastLogin(), "Last login should not be null");

        // Compare only up to seconds to avoid precision issues
        LocalDateTime expected = now.withNano(0);
        LocalDateTime actual = updatedUser.get().getLastLogin().withNano(0);
        assertEquals(expected, actual, "Last login time should match (ignoring nanoseconds)");
    }

    @Test
    public void testSave_NewUser_ShouldSaveAndReturnUser() {
        // Arrange
        User newUser = User.builder()
                .email("new@example.com")
                .password("newpassword")
                .firstname("New")
                .lastname("User")
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .build();

        // Act
        User savedUser = userRepository.save(newUser);

        // Assert
        assertNotNull(savedUser.getId(), "Saved user should have an ID");
        assertEquals("new@example.com", savedUser.getEmail(), "Email should match");
        assertEquals("New", savedUser.getFirstname(), "First name should match");
        assertEquals("User", savedUser.getLastname(), "Last name should match");
    }

    @Test
    public void testFindById_ExistingId_ShouldReturnUser() {
        // Act
        Optional<User> foundUser = userRepository.findById(testUser.getId());

        // Assert
        assertTrue(foundUser.isPresent(), "User should be found by ID");
        assertEquals(TEST_EMAIL, foundUser.get().getEmail(), "Email should match");
        assertEquals(TEST_FIRSTNAME, foundUser.get().getFirstname(), "First name should match");
        assertEquals(TEST_LASTNAME, foundUser.get().getLastname(), "Last name should match");
    }

    @Test
    public void testFindById_NonExistingId_ShouldReturnEmpty() {
        // Act
        Optional<User> foundUser = userRepository.findById(UUID.randomUUID());

        // Assert
        assertFalse(foundUser.isPresent(), "User should not be found for non-existent ID");
    }
}
