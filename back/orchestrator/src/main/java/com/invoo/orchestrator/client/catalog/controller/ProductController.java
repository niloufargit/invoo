package com.invoo.orchestrator.client.catalog.controller;

import com.invoo.global.catalog.product.EditedProductRequest;
import com.invoo.orchestrator.client.catalog.dto.products.ProductResponse;
import com.invoo.orchestrator.client.catalog.dto.products.ProductWithCategoryDto;
import com.invoo.orchestrator.client.catalog.service.ProductService;
import jakarta.validation.Valid;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Product related API endpoints
    @PostMapping()
    public ResponseEntity<?> save(@RequestBody ProductWithCategoryDto products) {
        log.info("Saving product: {}", products);
        return productService.createProduct(products);
    }

    @GetMapping("/catalog/{idCatalog}")
    public List<ProductResponse> getProductsByCatalogId(
            @Valid @PathVariable Long idCatalog) {
        return productService.getProductsByCatalogId(idCatalog);
    }

    @GetMapping("/category/{idCategory}")
    public List<ProductResponse> getProductsByCategoryId(@Valid @PathVariable Long idCategory) {
        return productService.getProductsByCategoryId(idCategory);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@Valid @PathVariable Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteProductById(@Valid @PathVariable Long id) {
        return productService.deleteProductById(id);
    }

    @PutMapping("/{id}")
    public EditedProductRequest updateProductById(
            @Valid @PathVariable Long id,
            @RequestBody EditedProductRequest productRequest
    ) {
        return productService.updateProductById(id, productRequest);
    }


}
