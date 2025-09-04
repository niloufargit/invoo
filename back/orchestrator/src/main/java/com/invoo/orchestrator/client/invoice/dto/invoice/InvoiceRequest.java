package com.invoo.orchestrator.client.invoice.dto.invoice;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class InvoiceRequest {
    private String invoiceNumber;
    private Long companyId;
    private Beneficiary beneficiary;
    private Payment payment;
    private String invoiceTitle;
    private LocalDateTime deliveryDate;
    private List<Product> products;
    private Price price;
}
