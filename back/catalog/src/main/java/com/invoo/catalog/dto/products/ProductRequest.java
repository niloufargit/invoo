package com.invoo.catalog.dto.products;

import com.invoo.catalog.entity.Product;

public record ProductRequest (
        String name,
        String description,
        String barcode,
        String reference,
        String htPrice,
        String vatRate
) {

    public Product toNewProduct() {
        return new Product(null, name, description, barcode, reference, htPrice,vatRate, null);
    }
}
