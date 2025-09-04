package com.invoo.orchestrator.infrastructure.repository;

import com.invoo.orchestrator.domaine.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TokenJpaRepository extends JpaRepository<Token, UUID> {

  @Query(value = """
      select t from Token t inner join User u\s
      on t.userUuid = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(UUID id);

    @Query(value = """
      select t from Token t
      where t.expired = true or t.revoked = true
      """)
    List<Token> findTokensExpiredAndRevoked();

  Optional<Token> findByToken(String token);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("UPDATE Token t SET t.expired = true, t.revoked = true WHERE  t.token = ?1")
  void killToken(String token);

  @Transactional
  @Modifying
  @Query("DELETE FROM Token t WHERE t.expired = true AND t.revoked = true")
  void deleteTokenWhenExpiredIsTrueAndRevokedIsTrue();
}
