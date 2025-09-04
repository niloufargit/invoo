package com.invoo.orchestrator.infrastructure.repository;

import com.invoo.orchestrator.domaine.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRepository extends JpaRepository<Address, Long> {
}
