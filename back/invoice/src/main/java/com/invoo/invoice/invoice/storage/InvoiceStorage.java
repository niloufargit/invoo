package com.invoo.invoice.invoice.storage;


import com.invoo.invoice.invoice.Invoice;
import com.invoo.invoice.invoice.repository.InvoiceRepository;
import org.springframework.stereotype.Component;

@Component
public class InvoiceStorage implements IInvoiceStorage{

    private final InvoiceRepository invoiceRepository;

    public InvoiceStorage(InvoiceRepository invoiceRepository) {this.invoiceRepository = invoiceRepository;}

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public void edit(Long id, String htmlContent) {
        invoiceRepository.updateInvoice(id, htmlContent);
    }

    @Override
    public void delete(Long id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public Invoice findById(Long id) {
        return invoiceRepository.findInvoiceById(id);
    }
}
