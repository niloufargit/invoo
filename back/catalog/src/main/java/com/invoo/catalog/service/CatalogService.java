package com.invoo.catalog.service;

import com.invoo.catalog.dto.catalog.CatalogResponse;
import com.invoo.global.catalog.CatalogsWithCategoriesDTO;
import com.invoo.catalog.entity.Catalog;
import com.invoo.catalog.repository.CatalogRepository;
import com.invoo.catalog.repository.CategoryRepository;
import com.invoo.global.catalog.CatalogRequest;
import com.invoo.global.catalog.CategoryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class CatalogService {

    private final CatalogRepository catalogRepository;
    private final CategoryRepository categoryRepository;

    public ResponseEntity<?> createCatalog(CatalogRequest catalogRequest) {
        log.info( "Starting to create Catalog : {}", catalogRequest );
        return ResponseEntity.ok(catalogRepository.save(toCatalog(catalogRequest)));
    }

    public ResponseEntity<?> getCatalogs(String id) {
        return ResponseEntity.ok(catalogRepository.getAllCatalogs(id));
    }

    public ResponseEntity<?> getCatalogById(Long id) {
        return ResponseEntity.ok(catalogRepository.findById(id));
    }

    public ResponseEntity<?> updateCatalog(@Valid Long id, @Valid CatalogRequest catalogRequest) {
        log.info( "Update catalog id = {}", id );
        log.info( "Update catalog = {}", catalogRequest );
        catalogRepository.updateCatalog(id, catalogRequest.name(), catalogRequest.description(), catalogRequest.reference());
        var cat = catalogRepository.getCatalogById( id );
        CatalogResponse response = CatalogResponse.from( cat.get() );
        log.info( "Response updated catalog = {}", response );
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> deleteCatalog(@Valid Long id) {
        catalogRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private Catalog toCatalog(CatalogRequest c) {
        return new Catalog(null, c.name(), c.description(),c.reference(), c.idCompany());
    }

    public ResponseEntity<?> getCompanyCatalogs(Long companyId) {

        List<CatalogsWithCategoriesDTO> response = new ArrayList<>();

        var catalogs = catalogRepository.getCompanyCatalogs(companyId);

        for (CatalogResponse catalog : catalogs) {
            List<CategoryDto> categories = categoryRepository.getAllCategoriesByIdCatalog( String.valueOf( catalog.id() ) );
            CatalogsWithCategoriesDTO catalogWithCategories = new CatalogsWithCategoriesDTO(
                    catalog.id(),
                    catalog.name(),
                    catalog.description(),
                    catalog.idCompany(),
                    categories
            );
            response.add(catalogWithCategories);
        }

        return ResponseEntity.ok(response);
    }
}
