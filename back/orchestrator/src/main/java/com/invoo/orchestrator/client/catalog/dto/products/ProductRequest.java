package com.invoo.orchestrator.client.catalog.dto.products;

public record ProductRequest (
        String name,
        String description,
        String barcode,
        String reference,
        String htPrice,
        String vatRate
) {

}
