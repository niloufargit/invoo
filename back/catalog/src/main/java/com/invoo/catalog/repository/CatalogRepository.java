package com.invoo.catalog.repository;

import com.invoo.catalog.dto.catalog.CatalogResponse;
import com.invoo.catalog.entity.Catalog;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    @Query("""
        select new com.invoo.catalog.dto.catalog.CatalogResponse(c.id,c.name,c.description,c.reference,c.idCompany) 
        from Catalog c
        where c.idCompany = ?1
                """)
    List<CatalogResponse> getAllCatalogs(String id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Catalog c SET c.name = ?2, c.description = ?3, c.reference = ?4 WHERE  c.id = ?1")
    void updateCatalog(
            @Valid Long id,
            @NotNull(message = "Catalog name is required. Should not be null")
            @NotBlank(message = "Catalog name is required. Should not be blank")
            String name,
            String description,
            @NotNull(message = "Catalog  reference is required. Should not be null")
            @NotBlank(message = "Catalog reference is required. Should not be blank")
            String reference);

    @Query("select c from Catalog c where c.id = ?1")
    Optional<Catalog> getCatalogById(Long id);


    @Query("""
        SELECT new com.invoo.catalog.dto.catalog.CatalogResponse(c.id,c.name,c.description,c.reference,c.idCompany)
                FROM Catalog c
                WHERE c.idCompany = ?1
        """)
    List<CatalogResponse> getCompanyCatalogs(Long companyId);


}
