package com.invoo.orchestrator.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test to verify that H2 database configuration works correctly
 * This test uses Spring Boot's auto-configuration for JDBC tests
 * which is more lightweight than DataJpaTest
 */
@org.springframework.boot.test.context.SpringBootTest(classes = {TestConfig.class})
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.datasource.driver-class-name=org.h2.Driver"
})
public class H2JdbcTest {

    @Autowired
    private DataSource dataSource;

    /**
     * Test that H2 database is configured correctly
     * This test verifies that:
     * 1. DataSource is injected
     * 2. The database connection can be established
     * 3. The database is H2
     */
    @Test
    public void testH2Database() throws SQLException {
        assertNotNull(dataSource, "DataSource should not be null");

        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection, "Connection should not be null");

            // Check database metadata to verify we're using H2
            String dbProductName = connection.getMetaData().getDatabaseProductName();
            assertEquals("H2", dbProductName, "Database should be H2");

            // Verify connection is valid
            assertTrue(connection.isValid(1), "Connection should be valid");
        }
    }
}
