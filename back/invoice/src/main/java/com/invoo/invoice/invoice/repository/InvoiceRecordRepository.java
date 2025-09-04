package com.invoo.invoice.invoice.repository;

import com.invoo.invoice.invoice.InvoiceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface InvoiceRecordRepository extends JpaRepository<InvoiceRecord, UUID> {

    @Query("SELECT c FROM InvoiceRecord c WHERE c.invoiceId = ?1")
    InvoiceRecord findByInvoiceId(Long invoiceId);

    @Query("select i from InvoiceRecord i where i.supplierCompanyId = ?1")
    InvoiceRecord findInvoiceByCompanyId(Long companyId);

    @Query("select i from InvoiceRecord i where i.supplierCompanyId = ?1")
    List<InvoiceRecord> findInvoicesByCompanyId(Long companyId);
}
