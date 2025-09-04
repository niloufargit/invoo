package com.invoo.orchestrator.infrastructure.repository;

import com.invoo.orchestrator.domaine.entity.ConfirmCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public interface ConfirmCodeRepository extends JpaRepository<ConfirmCode, Integer> {

  @Query(value = """
        select c from ConfirmCode c inner join User u
        on c.user.id = u.id
        where u.id = :id and c.expirationTime > current_timestamp
        """)
  List<ConfirmCode> findAllValidConfirmCodesByUser(Integer id);

  @Query(value = """
        select c from ConfirmCode c
        where c.expirationTime <= current_timestamp
        """)
  List<ConfirmCode> findExpiredConfirmCodes();

  Optional<ConfirmCode> findByConfirmCode(String confirmCode);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("UPDATE ConfirmCode c SET c.expirationTime = current_timestamp WHERE c.confirmCode = ?1")
  void expireConfirmCode(String confirmCode);

  @Transactional
  @Modifying
  @Query("DELETE FROM ConfirmCode c WHERE c.expirationTime <= current_timestamp")
  void deleteExpiredConfirmCodes();
}
