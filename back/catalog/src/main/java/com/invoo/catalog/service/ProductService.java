package com.invoo.catalog.service;

import com.invoo.catalog.domain.ProductValidator;
import com.invoo.catalog.dto.products.ProductResponse;
import com.invoo.catalog.dto.products.ProductWithCategoryDto;
import com.invoo.catalog.entity.Category;
import com.invoo.catalog.entity.Product;
import com.invoo.catalog.repository.CategoryRepository;
import com.invoo.catalog.repository.ProductRepository;
import com.invoo.global.catalog.product.EditedProductRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Product service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductValidator productValidator;


    /**
     * Save response entity.
     *
     * @param products the products
     * @return the response entity
     */
    public ResponseEntity<?> save(ProductWithCategoryDto products) {
        log.info( "saving products : {}", products );
        var idCategory = products.idCategory();
        var idCatalog = products.idCatalog();
        var productsList = products.products();

        if (idCategory == null ) {
            Category category = products.toNewCategory();
            category = categoryRepository.save(category);
            idCategory = category.getId();
        }

        List<Product> productsToSave = new ArrayList<>();
        List<ProductResponse> savedProducts = new ArrayList<>();
        for (var product : productsList) {
            var p = product.toNewProduct();
            p.setIdCategory(idCategory);
            productsToSave.add(p);
        }

        for (Product product : productsToSave) {
            var p = productRepository.save(product);
            ProductResponse productResponse = ProductResponse.from( p,idCatalog,idCategory );
            savedProducts.add(productResponse);
        }
        log.info( "Returned Product : {}", savedProducts );
        return ResponseEntity.ok(savedProducts);
    }

    /**
     * Gets products by catalog id.
     *
     * @param catalogId the catalog id
     * @return the products by catalog id
     */
    public List<ProductResponse> getProductsByCatalogId(Long catalogId) {
        productValidator.catalogExists(catalogId);
        return productRepository.getAllProductsByCatalogId(catalogId);
    }

    /**
     * Gets product by id.
     *
     * @param id the id
     * @return the product by id
     */
    public ResponseEntity<?> getProductById(Long id) {
        productValidator.productExists(id);
        return ResponseEntity.ok(productRepository.findById(id));
    }

    /**
     * Gets products by category id.
     *
     * @param categoryId the category id
     * @return the products by category id
     */
    public ResponseEntity<?> getProductsByCategoryId(Long categoryId) {
        productValidator.categoryExists(categoryId);
        return ResponseEntity.ok(productRepository.findByCategoryId(categoryId));
    }

    /**
     * Delete product by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    public ResponseEntity<?> deleteProductById(Long id) {
        productValidator.productExists(id);
        productRepository.deleteProductById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> updateProductById(Long id, @Valid EditedProductRequest productRequest) {
        {
            productValidator.productExists(id);
             productRepository.updateProductById(id, productRequest.name(),
                     productRequest.description(), productRequest.barcode(),
                     productRequest.reference(), productRequest.htPrice()
             , productRequest.vatRate(), productRequest.idCategory());
            var result = productRepository.findById(id);
            return ResponseEntity.ok().body(result);
        }
    }
}
