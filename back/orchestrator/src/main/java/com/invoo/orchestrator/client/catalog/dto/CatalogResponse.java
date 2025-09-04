package com.invoo.orchestrator.client.catalog.dto;

public record CatalogResponse(
        Long id,
        String name,
        String description,
        String reference,
        String idCompany
) {
}
