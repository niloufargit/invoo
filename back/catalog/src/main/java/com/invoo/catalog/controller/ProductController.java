package com.invoo.catalog.controller;

import com.invoo.catalog.dto.products.ProductResponse;
import com.invoo.catalog.dto.products.ProductWithCategoryDto;
import com.invoo.catalog.service.ProductService;
import com.invoo.global.catalog.product.EditedProductRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductWithCategoryDto products) {
        return productService.save(products);
    }

    @GetMapping("/catalog/{idCatalog}")
    public List<ProductResponse> getProductsByCatalogId(
            @Valid @PathVariable Long idCatalog) {

        return productService.getProductsByCatalogId(idCatalog);
    }

    @GetMapping("/category/{idCategory}")
    public ResponseEntity<?> getProductsByCategoryId(@Valid @PathVariable Long idCategory) {
        return productService.getProductsByCategoryId(idCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@Valid @PathVariable Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@Valid @PathVariable Long id) {
        return productService.deleteProductById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductById(@Valid @PathVariable Long id, @RequestBody @Valid EditedProductRequest productRequest) {
        log.info("Updating product with ID: {}", id);
        log.info("Product update request: {}", productRequest);
        return productService.updateProductById(id, productRequest);
    }
}
