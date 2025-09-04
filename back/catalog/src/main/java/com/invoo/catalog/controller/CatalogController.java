package com.invoo.catalog.controller;

import com.invoo.catalog.service.CatalogService;
import com.invoo.global.catalog.CatalogRequest;
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

@RestController
@RequestMapping("/catalogs")
@RequiredArgsConstructor
@Slf4j
public class CatalogController {

    private final CatalogService catalogService;

    @PostMapping()
    public ResponseEntity<?> createCatalog(@Valid @RequestBody CatalogRequest catalogRequest) {
        log.info( "Catalog controller : Create catalog" );
        return catalogService.createCatalog(catalogRequest);
    }

    @GetMapping("/entity/{id}")
    public ResponseEntity<?> getCatalogs(@PathVariable String id) {
        log.info( "Get catalog with id : {}", id );
        return catalogService.getCatalogs(id);
    }

    @GetMapping("company/{companyId}")
    public ResponseEntity<?> getCompanyCatalogs(@PathVariable Long companyId) {
        log.info("Start retrieve catalog with company id:  {}", companyId);
        return catalogService.getCompanyCatalogs(companyId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCatalogById(@Valid @PathVariable Long id) {
        log.info("Start retrieve catalog with id:  {}", id);
        return catalogService.getCatalogById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCatalog(@Valid @PathVariable Long id, @Valid @RequestBody CatalogRequest catalogRequest) {
        return catalogService.updateCatalog(id, catalogRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCatalog(@Valid @PathVariable Long id) {
        return catalogService.deleteCatalog(id);
    }

}
