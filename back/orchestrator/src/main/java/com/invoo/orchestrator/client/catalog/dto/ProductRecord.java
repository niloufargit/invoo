package com.invoo.orchestrator.client.catalog.dto;


public record ProductRecord (
        Long id,
        String name,
        String description,
        String barcode,
        String reference,
        String htPrice,
        String vatRate
) {

}
