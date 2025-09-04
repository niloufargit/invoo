package com.invoo.catalog.controller;

import com.invoo.catalog.dto.category.CategoryRequest;
import com.invoo.catalog.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping({"{idCatalog}"})
    public ResponseEntity<?> createCategory(
            @Valid @PathVariable Long idCatalog,
            @Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest, idCatalog);
    }

    @GetMapping({"/catalog/{idCatalog}"})
    public ResponseEntity<?> getCategories(@PathVariable String idCatalog) {
        return categoryService.getCategoriesByIdCatalog(idCatalog);
    }

}
