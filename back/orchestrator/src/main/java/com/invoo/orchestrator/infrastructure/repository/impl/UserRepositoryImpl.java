package com.invoo.orchestrator.infrastructure.repository.impl;

import com.invoo.orchestrator.infrastructure.repository.UserJpaRepository;
import com.invoo.orchestrator.domaine.repository.IUserRepository;
import com.invoo.orchestrator.domaine.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryImpl implements IUserRepository {

    private final UserJpaRepository repository;

    public UserRepositoryImpl(UserJpaRepository repository) {
        this.repository = repository;
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail( email );
    }

    @Override
    public UUID getUserId(String userName) {
        return repository.getUserId( userName );
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail( email );
    }

    @Override
    public Boolean userIsVerified(String email) {
        return repository.userIsVerified( email );
    }

    @Override
    public void updateLastLogin(UUID id, LocalDateTime now) {
        repository.updateLastLogin( id, now );
    }

    @Override
    public User save(User user) {
        return repository.save( user );
    }

    @Override
    public Optional<User> findById(UUID uuid) {
        return repository.findById( uuid );
    }

    @Override
    public void updateUserById(UUID id, String firstname, String lastname, String email) {
        repository.updateUserById( id, firstname, lastname, email);
    }
}
