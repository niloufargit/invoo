package com.invoo.catalog.repository;

import com.invoo.catalog.entity.Category;
import com.invoo.global.catalog.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Category c WHERE c.reference = ?1")
    boolean existsByReference(String reference);

    @Query("SELECT c FROM Category c WHERE c.reference = ?1")
    Category findByReference(String categoryReference);

    Category getCategoryByReferenceAndIdCatalog(String categoryReference, Long id);

    @Query("""
        SELECT new com.invoo.global.catalog.CategoryDto(c.id,c.name,c.description,c.reference,c.idCatalog)
        FROM Category c
        WHERE c.idCatalog = ?1
        """)
    List<CategoryDto> getAllCategoriesByIdCatalog(String idCatalog);
}
