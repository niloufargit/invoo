package com.invoo.catalog.service;

import com.invoo.catalog.dto.category.CategoryRequest;
import com.invoo.catalog.exception.ApplicationExceptions;
import com.invoo.catalog.repository.CatalogRepository;
import com.invoo.catalog.repository.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CatalogRepository catalogRepository;

    public ResponseEntity<?> createCategory(@Valid CategoryRequest categoryRequest, @Valid Long idCatalog) {

       if (categoryRepository.existsByReference( categoryRequest.reference() ))
           ApplicationExceptions.exist("Category with reference = " + categoryRequest.reference() + " already exists.",
                   HttpStatus.BAD_REQUEST);
       if (!catalogRepository.existsById(idCatalog))
              ApplicationExceptions.exist("Catalog with id = " + idCatalog + " does not exist.",
                     HttpStatus.BAD_REQUEST);

       var catalog = catalogRepository.findById(idCatalog).get();

       return ResponseEntity.ok(categoryRepository.save(categoryRequest.toCategory(catalog)));
    }

    public ResponseEntity<?> getCategoriesByIdCatalog(String idCatalog) {
        return ResponseEntity.ok(categoryRepository.getAllCategoriesByIdCatalog(idCatalog));
    }
}
