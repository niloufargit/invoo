package com.invoo.orchestrator.client.catalog.controller;

import com.invoo.orchestrator.client.catalog.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    // Catalog related API endpoints

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/catalog/{idCatalog}")
    public ResponseEntity<?> getCatalogs(@PathVariable String idCatalog) {
        return ResponseEntity.ok(categoryService.getCategories(idCatalog));
    }


}
