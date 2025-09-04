package com.invoo.invoice.invoice.repository;

import com.invoo.invoice.invoice.Invoice;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Transactional
    @Modifying
    @Query("update Invoice i set i.htmlContent = ?2 where i.id = ?1")
    void updateInvoice(Long id, String htmlContent);

    @Query("select i from Invoice i where i.id = ?1")
    Invoice findInvoiceById(Long id);
}
