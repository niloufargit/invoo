package com.invoo.orchestrator.domaine.poubelle.Entity.paiement;

import com.invoo.orchestrator.domaine.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByClient(User client);
}