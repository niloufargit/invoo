package com.invoo.orchestrator.client.catalog.dto.products;

public record ProductResponse (
        Long idCatalog,
        Long id,
        String name,
        String description,
        String barcode,
        String reference,
        String htPrice,
        String vatRate,
        Long idCategory
    ){}