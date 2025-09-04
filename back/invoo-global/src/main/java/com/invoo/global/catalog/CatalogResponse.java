package com.invoo.global.catalog;

import java.util.List;

public record CatalogResponse(
        Long id,
        String name,
        String description,
        String idCompany,
        List<CategoryDto> categories
) {
}
