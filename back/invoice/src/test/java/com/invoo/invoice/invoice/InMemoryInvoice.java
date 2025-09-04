package com.invoo.invoice.invoice;

import com.invoo.invoice.invoice.storage.IInvoiceStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InMemoryInvoice implements IInvoiceStorage {
    public List<Invoice> invoices = new ArrayList<>();

    @Override
    public Invoice save(Invoice invoice) {
        var length = invoices.size();
        if (invoices.contains(invoice)) {
            return invoice;
        }
        invoice.setId(length + 1L);
        invoices.add(invoice);
        return invoice;
    }

    @Override
    public void edit(Long id, String htmlContent) {
        var inv = findById(id);
        if (inv != null) {
            inv.setHtmlContent(htmlContent);
        }
    }

    @Override
    public void delete(Long id) {
        invoices.removeIf(invoice -> Objects.equals(invoice.getId(), id));
    }

    @Override
    public Invoice findById(Long invoiceId) {
        return invoices.stream()
                .filter(invoice -> invoice.getId().equals(invoiceId))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + invoiceId));
    }
}
