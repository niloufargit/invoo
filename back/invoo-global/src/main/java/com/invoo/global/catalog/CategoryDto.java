package com.invoo.global.catalog;

public record CategoryDto(
        Long id,
        String name,
        String description,
        String reference,
        Long idCatalog
) {
}
