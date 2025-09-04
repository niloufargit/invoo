package com.invoo.invoice.invoice.storage;

import com.invoo.invoice.invoice.InvoiceRecord;

import java.util.List;

public interface IInvoiceRecordStorage {
    InvoiceRecord save(InvoiceRecord record);
    InvoiceRecord findByInvoiceId(Long id);
    InvoiceRecord findInvoiceByCompanyId(Long companyId);

    List<InvoiceRecord> findAllByCompanyId(Long companyId);
}
