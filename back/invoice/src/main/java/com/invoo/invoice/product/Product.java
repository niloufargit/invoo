package com.invoo.invoice.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Product {
    private String name;
    private String description;
    private int quantity;
    private double htPrice;
    private double vatRate;
}