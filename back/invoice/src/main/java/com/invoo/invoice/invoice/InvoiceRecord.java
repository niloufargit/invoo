package com.invoo.invoice.invoice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class InvoiceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long invoiceId;
    private Long supplierCompanyId;
    private String customerId;
    private String invoiceNumber;
    private String invoiceTitle;
    private double totalIncludingTax;
    private double totalExcludingTax;
}
