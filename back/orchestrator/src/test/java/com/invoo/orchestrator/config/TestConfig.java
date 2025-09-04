package com.invoo.orchestrator.config;

import com.invoo.orchestrator.application.config.auditing.ApplicationAuditAware;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

/**
 * Test configuration class that provides the necessary beans for testing
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = "com.invoo.orchestrator.domaine.entity")
@EnableJpaRepositories(basePackages = "com.invoo.orchestrator.infrastructure.repository")
public class TestConfig {

    /**
     * Mock ApplicationAuditAware bean for testing
     * @return AuditorAware<UUID> that always returns a test user ID
     */
    @Bean
    public AuditorAware<UUID> auditorAware() {
        return new ApplicationAuditAware() {
            @Override
            public Optional<UUID> getCurrentAuditor() {
                return Optional.of(UUID.randomUUID()); // Random UUID for testing
            }
        };
    }

    /**
     * Mock UserDetails bean for testing
     * @return UserDetails for a test user
     */
    @Bean
    public UserDetails testUserDetails() {
        return User.builder()
                .username("testuser")
                .password("password")
                .authorities(new ArrayList<>())
                .build();
    }
}
