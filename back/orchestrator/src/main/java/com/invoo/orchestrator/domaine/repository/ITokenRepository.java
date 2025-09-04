package com.invoo.orchestrator.domaine.repository;

import com.invoo.orchestrator.domaine.entity.Token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ITokenRepository {
    List<Token> findAllValidTokenByUser(UUID id);
    List<Token> findTokensExpiredAndRevoked();
    Optional<Token> findByToken(String token);
    void killToken(String token);
    void deleteTokenWhenExpiredIsTrueAndRevokedIsTrue();
    void save(Token token);
    void saveAll(List<Token> validUserTokens);
}
