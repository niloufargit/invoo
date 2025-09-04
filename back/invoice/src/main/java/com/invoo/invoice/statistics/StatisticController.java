package com.invoo.invoice.statistics;

import com.invoo.invoice.statistics.products.domaine.ProductsStatisticsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoices/statistics")
@Slf4j
@AllArgsConstructor
public class StatisticController {

    private final ProductsStatisticsService productsStatisticsService;


    @GetMapping("/products/{companyId}")
    public ResponseEntity<?> getProductsStatistics(@PathVariable Long companyId) {
        log.info("Retrieving products statistics");
        return productsStatisticsService.getProductsStatistics(companyId);
    }

}
