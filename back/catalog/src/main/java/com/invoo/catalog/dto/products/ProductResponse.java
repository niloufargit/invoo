package com.invoo.catalog.dto.products;

import com.invoo.catalog.entity.Product;

public record ProductResponse (
        Long idCatalog,
        Long id,
        String name,
        String description,
        String barcode,
        String reference,
        String htPrice,
        String vatRate,
        Long idCategory
    ){

    public static ProductResponse from(Product product , Long idCatalog, Long idCategory) {
        return new ProductResponse(
                idCatalog,
                product.getId(),
                product.getName(),
                product.getDescription(), product.getBarcode(), product.getReference(), product.getHtPrice(),
                product.getVatRate(), idCategory

                );
    }
}