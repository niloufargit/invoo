package com.invoo.global.catalog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CatalogRequest(

        Long id,

        @NotNull(message = "Catalog name is required. Should not be null")
        @NotBlank(message = "Catalog name is required. Should not be blank")
        String name,

        String description,

        @NotNull(message = "Catalog  reference is required. Should not be null")
        @NotBlank(message = "Catalog reference is required. Should not be blank")
        String reference,

        @NotNull(message = "Company ID is required. Should not be null")
        @NotBlank(message = "Company ID is required. Should not be blank")
        String idCompany
) {

}
