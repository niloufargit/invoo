package com.invoo.global.catalog.product;

public record EditedProductRequest(
        Long id,
        String name,
        String description,
        String barcode,
        String reference,
        String htPrice,
        String vatRate,
        Long idCategory
) {

}
