package com.invoo.orchestrator.client.catalog.dto;

import java.util.List;


public record CategoryRecord(
        Long id,
        String name,
        String description,
        String reference,
        List<ProductRecord> products
) {
}
