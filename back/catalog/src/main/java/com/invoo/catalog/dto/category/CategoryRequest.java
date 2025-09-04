package com.invoo.catalog.dto.category;

import com.invoo.catalog.entity.Catalog;
import com.invoo.catalog.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest (
        @NotNull(message = "Category name is required. Should not be null")
        @NotBlank(message = "Category name is required. Should not be blank")
        String name,
        String description,
        @NotNull(message = "Category reference is required. Should not be null")
        @NotBlank(message = "Category reference is required. Should not be blank")
        String reference
){
    public Category toCategory() {
        return new Category(null, name, description, reference, null);
    }

    public Category toCategory(Catalog catalog) {
        return new Category(null, name, description, reference, catalog.getId());
    }
}
