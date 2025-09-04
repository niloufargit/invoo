package com.invoo.payment.repository;

import com.invoo.payment.payment.entity.StripeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface IStripeRecordRepository extends JpaRepository<StripeRecord, UUID> {

    @Query("SELECT s FROM StripeRecord s WHERE s.paymentId = ?1")
    StripeRecord findByPaymentId(UUID uuid);
}
