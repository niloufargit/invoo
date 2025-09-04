package com.invoo.orchestrator.client.invoice.dto.invoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Payment {
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private String paymentReference;
    private double paymentAmount;
    private String paymentCurrency;
    private String paymentStatus;
    private String paymentDetails;
    private String paymentTerms;
    private double latePaymentPenalty;
    private double settlementDiscount;
}