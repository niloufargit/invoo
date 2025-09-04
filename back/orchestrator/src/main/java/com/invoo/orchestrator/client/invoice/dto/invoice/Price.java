package com.invoo.orchestrator.client.invoice.dto.invoice;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Price {
    private double unitPrice;
    private double totalExcludingTax;
    private String vatRate;
    private double totalIncludingTax;
    private double discount;
}