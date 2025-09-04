package com.invoo.orchestrator.client.catalog.dto.products;

import java.util.List;

public record ProductWithCategoryDto(
        Long idCategory,
        String name,
        String description,
        String reference,
        Long idCatalog,
        List<ProductRequest> products
) {
}
