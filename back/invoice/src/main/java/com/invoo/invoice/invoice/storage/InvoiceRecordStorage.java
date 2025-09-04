package com.invoo.invoice.invoice.storage;

import com.invoo.invoice.invoice.InvoiceRecord;
import com.invoo.invoice.invoice.repository.InvoiceRecordRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InvoiceRecordStorage implements IInvoiceRecordStorage{

    private final InvoiceRecordRepository  invoiceRecordRepository;

    public InvoiceRecordStorage(InvoiceRecordRepository invoiceRecordRepository) {
        this.invoiceRecordRepository = invoiceRecordRepository;
    }

    @Override
    public InvoiceRecord save(InvoiceRecord record) {
        return invoiceRecordRepository.save( record );
    }

    @Override
    public InvoiceRecord findByInvoiceId(Long id) {
        return invoiceRecordRepository.findByInvoiceId(id);
    }

    @Override
    public InvoiceRecord findInvoiceByCompanyId(Long companyId) {
        return invoiceRecordRepository.findInvoiceByCompanyId(companyId);
    }

    @Override
    public List<InvoiceRecord> findAllByCompanyId(Long companyId) {
        return invoiceRecordRepository.findInvoicesByCompanyId(companyId);
    }
}
