package com.invoo.invoice.invoice.storage;

import com.invoo.invoice.invoice.Invoice;
import org.springframework.stereotype.Component;

@Component
public interface IInvoiceStorage {
    Invoice save(Invoice invoice);
    void edit(Long id, String htmlContent);
    void delete(Long id);
    Invoice findById(Long id);
}
