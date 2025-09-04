package com.invoo.catalog.domain;

import com.invoo.catalog.exception.ApplicationExceptions;
import com.invoo.catalog.repository.CatalogRepository;
import com.invoo.catalog.repository.CategoryRepository;
import com.invoo.catalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductValidator {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CatalogRepository catalogRepository;

    public void catalogExists(Long catalogId){
        if(catalogId == null){
            throw new ApplicationExceptions("Product must have a catalogId", HttpStatus.BAD_REQUEST);
        }
        boolean exists = catalogRepository.existsById(catalogId);
        if(!exists){
            throw new ApplicationExceptions("Catalog does not exist", HttpStatus.NOT_FOUND);
        }
    }

    public void categoryExists(Long categoryId){
        if(categoryId == null){
            throw new ApplicationExceptions("Product must have a categoryId", HttpStatus.BAD_REQUEST);
        }
        boolean exists = categoryRepository.existsById(categoryId);
        if(!exists){
            throw new ApplicationExceptions("Category does not exist", HttpStatus.NOT_FOUND);
        }
    }

    public void productExists(Long productId){
        if(productId == null){
            throw new ApplicationExceptions("Product must have a productId", HttpStatus.BAD_REQUEST);
        }
        boolean exists = productRepository.existsById(productId);
        if(!exists){
            throw new ApplicationExceptions("Product does not exist", HttpStatus.NOT_FOUND);
        }
    }

//    public void validateProductUpdate(Product existingProduct, ProductRequest productRequest) {
//        boolean isDifferent = !existingProduct.getName().equals(productRequest.name()) ||
//                !existingProduct.getDescription().equals(productRequest.description()) ||
//                !existingProduct.getBarcode().equals(productRequest.barcode()) ||
//                !existingProduct.getReference().equals(productRequest.reference()) ||
//                !existingProduct.getHtPrice().equals(productRequest.htPrice());
//
//        if (!isDifferent) {
//            throw new ApplicationExceptions("No values are different from the existing ones", HttpStatus.BAD_REQUEST);
//        }
//    }
}