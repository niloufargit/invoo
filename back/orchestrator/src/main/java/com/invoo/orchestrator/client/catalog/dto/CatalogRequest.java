package com.invoo.orchestrator.client.catalog.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CatalogRequest(
        Long id,

        @NotNull(message = "Company ID is required. Should not be null")
        @NotBlank(message = "Company ID is required. Should not be blank")
        String name,

        String description,

        @NotNull(message = "Company ID is required. Should not be null")
        @NotBlank(message = "Company ID is required. Should not be blank")
        String idCompany
) {
}
