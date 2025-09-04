package com.invoo.invoice.price;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Price {
    private double unitPrice;
    private double totalExcludingTax;
    private double totalIncludingTax;
}