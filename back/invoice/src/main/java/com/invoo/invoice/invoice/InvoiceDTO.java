package com.invoo.invoice.invoice;

import com.invoo.invoice.beneficiaryDTO.Beneficiary;
import com.invoo.invoice.invoice.provider.IProviderDate;
import com.invoo.invoice.company.Company;
import com.invoo.invoice.payment.Payment;
import com.invoo.invoice.price.Price;
import com.invoo.invoice.product.Product;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {
    private Long id;
    private String invoiceNumber;
    private LocalDateTime invoiceDate;
    private Company company;
    private Beneficiary beneficiary;
    private String invoiceTitle;
    private LocalDateTime deliveryDate;
    private List<Product> products;
    private Price price;

    public InvoiceDTO(Long id, String invoiceNumber, Company companyId, Beneficiary beneficiary, String invoiceTitle, LocalDateTime deliveryDate, List<Product> products, Price price, IProviderDate dateProvider) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = dateProvider.generateCurrentDate();
        this.company = companyId;
        this.beneficiary = beneficiary;
        this.invoiceTitle = invoiceTitle;
        this.deliveryDate = deliveryDate;
        this.products = products;
        this.price = price;
    }
}