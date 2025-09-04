package com.invoo.orchestrator.domaine.poubelle.Entity.transactionRequest;

import com.invoo.orchestrator.domaine.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRequestRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByClient(User client);
    List<Transaction> findBysupplier(User supplier);
}


