package com.invoo.catalog;

import com.invoo.catalog.dto.products.ProductRequest;
import com.invoo.catalog.dto.products.ProductWithCategoryDto;
import com.invoo.catalog.exception.ApplicationExceptions;
import com.invoo.catalog.repository.ProductRepository;
import com.invoo.catalog.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProductApplicationTests {

    @Autowired
    ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void saveProductWithNonExistentCategoryId_ShouldCreateCategoryThenSaveProduct(){
        // GIVEN a non-existent category id
        Long nonExistentCategoryId = 999L;
        // WHEN saveProduct is called with a non-existent category id
        // THEN a new category should be created and the product should be saved
        productService.save( new ProductWithCategoryDto(
                nonExistentCategoryId,
                "categoryName",
                "categoryDescription",
                "categoryReference",
                1L,
                List.of(new ProductRequest("productName", "productDescription", "productReference", "testReference","1.99"))
        ));
        System.out.println(productRepository.findAll());
    }

    @Test
    public void getProductsByNonExistentCatalogId_ShouldThrowException() {
        // GIVEN a non-existent catalog id
        Long nonExistentCatalogId = 999L;
        // THEN an exception should be thrown when getProductsByCatalogId is called
        Assertions.assertThrows(ApplicationExceptions.class, () -> productService.getProductsByCatalogId(nonExistentCatalogId));
    }

/*    @Test
    public void getProductsByExistentCatalogId_ShouldNotThrowException() {
        // GIVEN an existent catalog id
        Long existentCatalogId = 1L;
        // THEN no exception should be thrown when getProductsByCatalogId is called
        Assertions.assertDoesNotThrow(() -> productService.getProductsByCatalogId(existentCatalogId));
    }*/

    @Test
    public void getProductsByNonExistentCategoryId_ShouldThrowException() {
        // GIVEN a non-existent category id
        Long nonExistentCategoryId = 999L;
        // THEN an exception should be thrown when getProductsByCategoryId is called
        Assertions.assertThrows(ApplicationExceptions.class, () -> productService.getProductsByCategoryId(nonExistentCategoryId));
    }

/*    @Test
    public void getProductsByExistentCategoryId_ShouldNotThrowException() {
        // GIVEN an existent category id
        Long existentCategoryId = 1L;
        // THEN no exception should be thrown when getProductsByCategoryId is called
        Assertions.assertDoesNotThrow(() -> productService.getProductsByCategoryId(existentCategoryId));
    }*/

    @Test
    public void deleteProductByNonExistentProductId_ShouldThrowException() {
        // GIVEN a non-existent product id
        Long nonExistentProductId = 999L;
        // THEN an exception should be thrown when deleteProduct is called
        Assertions.assertThrows(ApplicationExceptions.class, () -> productService.deleteProductById(nonExistentProductId));
    }

    @Test
    public void deleteProductByExistentProductId_ShouldNotThrowException() {
        // GIVEN an existent product id
        Long existentProductId = 1L;
        // THEN no exception should be thrown when deleteProduct is called
        Assertions.assertDoesNotThrow(() -> productService.deleteProductById(existentProductId));
    }

}
