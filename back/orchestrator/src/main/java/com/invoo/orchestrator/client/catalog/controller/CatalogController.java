package com.invoo.orchestrator.client.catalog.controller;

import com.invoo.global.catalog.CatalogRequest;
import com.invoo.global.catalog.CatalogsWithCategoriesDTO;
import com.invoo.orchestrator.client.catalog.dto.CatalogResponse;
import com.invoo.orchestrator.client.catalog.service.CatalogService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalogs")
@Slf4j
public class CatalogController {
    // Catalog related API endpoints

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    // API endpoints

    @PostMapping()
    public ResponseEntity<?> createCatalog(@Valid @RequestBody CatalogRequest catalogRequest) {
        return catalogService.createCatalog(catalogRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCatalog(
            @PathVariable Long id,
            @Valid @RequestBody CatalogRequest catalogRequest) {
        return catalogService.updateCatalog(catalogRequest, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCatalogById(@PathVariable Long id) {
        return catalogService.deleteCatalogById(id);
    }

    @GetMapping("/{id}")
    public CatalogResponse getCatalogById(@PathVariable Long id) {
        return catalogService.getCatalogById(id);
    }

    @GetMapping("/entity/{id}")
    public List<CatalogResponse> getCatalogs(@PathVariable String id) {
        return catalogService.getCatalogs(id);
    }

    @GetMapping("/company/{companyId}")
    public List<CatalogsWithCategoriesDTO> getCompanyCatalogs(@PathVariable Long companyId) {
        log.info("Start retrieve catalog with company id:  {}", companyId);
        return catalogService.getCompanyCatalogs(companyId);
    }



    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadExcelFile(
            @RequestPart(name = "file") MultipartFile file,
            @RequestPart(name = "catalogRequest") CatalogRequest catalogRequest
    ) {
        return catalogService.processExcelFile( file, catalogRequest ) ;
    }
}
