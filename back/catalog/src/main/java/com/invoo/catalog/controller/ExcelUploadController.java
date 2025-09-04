package com.invoo.catalog.controller;

import com.invoo.global.catalog.CatalogRequest;
import com.invoo.catalog.service.ExcelProcessingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ExcelUploadController {

    private final ExcelProcessingService excelProcessingService;

    public ExcelUploadController(ExcelProcessingService excelProcessingService) {
        this.excelProcessingService = excelProcessingService;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<?> uploadExcelFile(
            @Valid @RequestPart(name = "file") MultipartFile file,
            @Valid @RequestPart(name = "catalogRequest") CatalogRequest catalogRequest
    ) {
        return excelProcessingService.processExcelFile( file, catalogRequest ) ;
    }
}
