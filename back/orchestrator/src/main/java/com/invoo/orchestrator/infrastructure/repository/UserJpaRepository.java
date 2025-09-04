package com.invoo.orchestrator.infrastructure.repository;

import com.invoo.orchestrator.domaine.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<User, UUID> {


    Optional<User> findByEmail(String email);

    @Query("SELECT u.id FROM User u WHERE u.email = :userName")
    UUID getUserId(String userName);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    boolean existsByEmail(String email);

    @Query("SELECT u.enabled FROM User u WHERE u.email = :email")
    Boolean userIsVerified(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.lastLogin = :now WHERE u.id = :id")
    void updateLastLogin(UUID id, LocalDateTime now);

    @Transactional
    @Modifying
    @Query("""
    UPDATE User u SET u.firstname = :firstname ,u.lastname = :lastname , u.email = :email WHERE u.id = :id
    """)
    void updateUserById(UUID id, String firstname, String lastname, String email);
}
