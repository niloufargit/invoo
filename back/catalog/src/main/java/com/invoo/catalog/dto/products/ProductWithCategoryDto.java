package com.invoo.catalog.dto.products;

import com.invoo.catalog.entity.Category;

import java.util.List;

public record ProductWithCategoryDto(
        Long idCategory,
        String name,
        String description,
        String reference,
        Long idCatalog,
        List<ProductRequest> products
) {

    public Category toNewCategory() {
        return new Category(null, name, description, reference, idCatalog);
    }

}
