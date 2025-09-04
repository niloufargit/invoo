package com.invoo.catalog.dto.catalog;

import com.invoo.catalog.entity.Catalog;

public record CatalogResponse(
        Long id,
        String name,
        String description,
        String reference,
        String idCompany
) {

    public static CatalogResponse from(Catalog c) {
        return new CatalogResponse( c.getId(), c.getName(), c.getDescription(), c.getReference(), c.getIdCompany() );
    }
}
