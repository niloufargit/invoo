package com.invoo.payment.repository;

import com.invoo.payment.payment.entity.Payment;
import com.invoo.payment.payment.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IPaymentRequestRepository extends JpaRepository<Payment, UUID> {

    @Modifying
    @Query("UPDATE Payment p SET p.status = ?2 WHERE p.id = ?1")
    void updatePaymentByIdAndStatus(UUID uuid, Status status);

    @Query("SELECT p.id FROM Payment p WHERE p.status = 'CREATED' OR p.status = 'UNPAID'")
    List<UUID> getAllPaymentsByStatusCreatedOrUnpaid();

    List<Payment> getAllPaymentRequestsByCompanyId(Long companyId);

    @Query("SELECT p FROM Payment p WHERE p.recipientId = :id")
    List<Payment> getAllPaymentRequestsReceivedByCompanyId(@Param("id") Long id);
}
