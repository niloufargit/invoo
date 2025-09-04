package com.invoo.catalog.repository;

import com.invoo.catalog.dto.products.ProductResponse;
import com.invoo.catalog.entity.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        SELECT new com.invoo.catalog.dto.products.ProductResponse(cate.idCatalog,p.id, p.name,p.description,p.barcode,p.reference,p.htPrice,p.vatRate,p.idCategory)
        FROM Product p
        INNER JOIN Category cate ON p.idCategory = cate.id
        inner join Catalog cata on cate.idCatalog = cata.id
        where cata.id=:idCatalog
        
""")
    List<ProductResponse> getAllProductsByCatalogId(@Valid @Param("idCatalog") Long idCatalog);

    @Query("""
        SELECT p
        FROM Product p
        INNER JOIN Category cate ON p.idCategory = cate.id
        where cate.id=:idCategory
""")
    List<Product> findByCategoryId(@Valid @Param("idCategory") Long idCategory);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Product WHERE id = :id")
    void deleteProductById(Long id);

    @Modifying
    @Transactional
    @Query("""
        UPDATE Product p
        SET p.name = :name, p.description = :description, p.barcode = :barcode, p.reference = :reference, p.htPrice = :htPrice, p.vatRate = :vatRate, p.idCategory = :idCategory
        WHERE p.id = :id
        """)
    void updateProductById(
            @Param("id") Long id,
            @NotNull(message = "Product name is required. Should not be null")
            @NotBlank(message = "Product name is required. Should not be blank")
            @Param("name") String name,
            @NotNull(message = "Product description is required. Should not be null")
            @Param("description") String description,
            @NotNull(message = "Product barcode is required. Should not be null")
            @Param("barcode") String barcode,
            @NotNull(message = "Product reference is required. Should not be null")
            @Param("reference") String reference,
            @NotNull(message = "Product htPrice is required. Should not be null")
            @Param("htPrice") String htPrice,
            @NotNull(message = "Product vatRate is required. Should not be null")
            @Param("vatRate") String vatRate,
            Long idCategory
    );
}
