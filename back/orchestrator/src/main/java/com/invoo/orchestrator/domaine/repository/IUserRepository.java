package com.invoo.orchestrator.domaine.repository;

import com.invoo.orchestrator.domaine.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository {
    Optional<User> findByEmail(String email);
    UUID getUserId(String userName);
    boolean existsByEmail(String email);
    Boolean userIsVerified(String email);
    void updateLastLogin(UUID id, LocalDateTime now);
    User save(User user);
    Optional<User> findById(UUID uuid);

    void updateUserById(UUID id, String firstname, String lastname, String email);
}
