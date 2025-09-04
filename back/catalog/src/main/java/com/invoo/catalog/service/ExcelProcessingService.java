package com.invoo.catalog.service;

import com.invoo.global.catalog.CatalogRequest;
import com.invoo.catalog.entity.Catalog;
import com.invoo.catalog.entity.Category;
import com.invoo.catalog.entity.Product;
import com.invoo.catalog.exception.ApplicationExceptions;
import com.invoo.catalog.repository.CatalogRepository;
import com.invoo.catalog.repository.CategoryRepository;
import com.invoo.catalog.repository.ProductRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ExcelProcessingService {

    private final CatalogRepository catalogRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ExcelProcessingService(CatalogRepository catalogRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.catalogRepository = catalogRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> processExcelFile(MultipartFile file, CatalogRequest catalogRequest) {

        List<Map<String, Object>> data = new ArrayList<>();
        Catalog catalog = null;
        if (Objects.isNull(catalogRequest.id()) || catalogRequest.id().toString().isEmpty()) {
            catalog = toCatalog(catalogRequest);
            catalog = catalogRepository.save(catalog);
        } else {
            var c = catalogRepository.getCatalogById(catalogRequest.id());
            if (c.isEmpty()) {
                ApplicationExceptions.exist(
                        "Catalog not found with id = " + catalogRequest.id(),
                        HttpStatus.NOT_FOUND
                );
            }
            catalog = c.get();
        }

        try {
            XSSFWorkbook workbook   = new XSSFWorkbook(file.getInputStream());
            int sheetIndex = workbook.getActiveSheetIndex();

            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);

            List<String>  headers      = new ArrayList<>();
            Iterator<Row> rowsIterator = sheet.rowIterator();
            // Get the headers of the sheet
            rowsIterator.next().forEach(cell -> headers.add(cell.getStringCellValue()));

            while (rowsIterator.hasNext()) {
                Row row = rowsIterator.next();
                Map<String, Object> rowData = new HashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    //Check the cell type and format accordingly
                    switch (row.getCell( i ).getCellType()) {
                        case NUMERIC -> rowData.put( headers.get( i ),
                                new BigDecimal(Double.toString(row.getCell( i ).getNumericCellValue())) );
                        case STRING  -> rowData.put( headers.get( i ), row.getCell( i ).getStringCellValue() );
                        case BOOLEAN -> rowData.put( headers.get( i ), row.getCell( i ).getBooleanCellValue() );
                    }
                }
                processRowData(rowData, catalog);
                data.add(rowData);
            }

        } catch (IOException e) {
            ApplicationExceptions.processingFile("Error processing the file", HttpStatus.BAD_REQUEST );
        }

        return ResponseEntity.ok("File processed successfully");

    }

    private void processRowData(Map<String, Object> rowData, Catalog catalog) {

        String category_name = (String) rowData.get("Category_name");
        String category_description = (String) rowData.get("Category_description");
        String category_reference = (String) rowData.get("Category_reference");

        String product_htPrice = (String) rowData.get("Product_htPrice");
        String product_barcode = (String) rowData.get("Product_barcode");
        String product_description  = (String) rowData.get("Product_description");
        String product_reference = (String) rowData.get("Product_reference");
        String product_name = (String) rowData.get("Product_name");
        String product_vatRate = (String) rowData.get("Product_vatRate");

        Product product = new Product(
                null,product_name, product_description, product_barcode,
                product_reference, product_htPrice, product_vatRate, null);

        Category c = categoryRepository.getCategoryByReferenceAndIdCatalog(category_reference, catalog.getId());

        if (c == null) {
            c = new Category(null, category_name, category_description, category_reference, catalog.getId());
            categoryRepository.save(c);
        }

        product.setIdCategory( c.getId() );
        productRepository.save( product );
    }

    private Catalog toCatalog(CatalogRequest c) {
        return new Catalog(null, c.name(), c.description(),c.reference(), c.idCompany());
    }
    
}
