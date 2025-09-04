package com.invoo.orchestrator.client.catalog.dto;

import java.util.List;

public record CatalogRecord(
        Long id,
        String name,
        String description,
        String idCompany,
        List<CategoryRecord> categories
) {
}
