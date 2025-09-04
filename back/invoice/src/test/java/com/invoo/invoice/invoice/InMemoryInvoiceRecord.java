package com.invoo.invoice.invoice;

import com.invoo.invoice.invoice.storage.IInvoiceRecordStorage;

import java.util.ArrayList;
import java.util.List;

public class InMemoryInvoiceRecord implements IInvoiceRecordStorage {

    public List<InvoiceRecord> invoiceRecords = new ArrayList<>();


    @Override
    public InvoiceRecord save(InvoiceRecord e) {
        invoiceRecords.add( e );
        return e;
    }

    @Override
    public InvoiceRecord findByInvoiceId(Long id) {
        return null;
    }

    @Override
    public InvoiceRecord findInvoiceByCompanyId(Long id) {
        return null;
    }

    @Override
    public List<InvoiceRecord> findAllByCompanyId(Long companyId) {
        return List.of();
    }
}
