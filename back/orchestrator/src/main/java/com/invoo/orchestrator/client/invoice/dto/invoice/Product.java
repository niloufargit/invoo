package com.invoo.orchestrator.client.invoice.dto.invoice;

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
    private String htPrice;
    private String vatRate;
    private Price price;
}
