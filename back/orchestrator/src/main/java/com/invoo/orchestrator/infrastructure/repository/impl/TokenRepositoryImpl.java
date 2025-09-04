package com.invoo.orchestrator.infrastructure.repository.impl;

import com.invoo.orchestrator.infrastructure.repository.TokenJpaRepository;
import com.invoo.orchestrator.domaine.repository.ITokenRepository;
import com.invoo.orchestrator.domaine.entity.Token;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TokenRepositoryImpl implements ITokenRepository {

    private final TokenJpaRepository repository;

    public TokenRepositoryImpl(TokenJpaRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Token> findAllValidTokenByUser(UUID id) {
        return repository.findAllValidTokenByUser( id );
    }

    @Override
    public List<Token> findTokensExpiredAndRevoked() {
        return repository.findTokensExpiredAndRevoked();
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return repository.findByToken( token );
    }

    @Override
    public void killToken(String token) {
        repository.killToken( token );
    }

    @Override
    public void deleteTokenWhenExpiredIsTrueAndRevokedIsTrue() {
        repository.deleteTokenWhenExpiredIsTrueAndRevokedIsTrue();
    }

    @Override
    public void save(Token token) {
        repository.save( token );
    }

    @Override
    public void saveAll(List<Token> validUserTokens) {
        repository.saveAll( validUserTokens );
    }
}
