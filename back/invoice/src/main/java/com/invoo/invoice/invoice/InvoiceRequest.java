package com.invoo.invoice.invoice;

import com.invoo.invoice.beneficiaryDTO.Beneficiary;
import com.invoo.invoice.payment.Payment;
import com.invoo.invoice.price.Price;
import com.invoo.invoice.product.Product;
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
