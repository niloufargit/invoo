package com.invoo.invoice.statistics.products.domaine;

import com.invoo.global.statistic.ProductStatisticDTO;
import com.invoo.invoice.invoice.InvoiceRequest;
import com.invoo.invoice.statistics.products.ProductStatistic;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProductsStatisticsService {


    private final ProductsStatisticsRepository repository;


    public ResponseEntity<?> getProductsStatistics(Long companyId) {
        var productStat = repository.getProductStatisticTop10WithCompany(companyId);

        if (productStat.isEmpty()) {
            log.warn("No product statistics found.");
            return ResponseEntity.ok( List.of() );
        }



        var totalQuantity = productStat.stream()
                .mapToInt(ProductStatistic::getQuantity)
                .sum();

        var totalPriceHT = productStat.stream()
                .mapToDouble(ProductStatistic::getTotalPriceHT)
                .sum();
        var totalPriceTTC = productStat.stream()
                .mapToDouble(ProductStatistic::getTotalPriceTTC)
                .sum();

        /*
        100% --- total
          x% --- unit   --> x = unit * 100 / total
         */
        List<ProductStatisticDTO> productStatisticDTOs = productStat.stream()
                .map(stat -> new ProductStatisticDTO(
                        stat.getId(),
                        stat.getCompanyId(),
                        stat.getProductName(),
                        stat.getQuantity(),
                        stat.getTotalPriceHT(),
                        stat.getTotalPriceTTC(),
                        (int) ((stat.getQuantity() * 100.0) / totalQuantity),
                        (stat.getTotalPriceHT() * 100.0) / totalPriceHT,
                        (stat.getTotalPriceTTC() * 100.0) / totalPriceTTC
                ))
                .toList();


        return ResponseEntity.ok( productStatisticDTOs );
    }


    public void saveProductStatistics(InvoiceRequest input, Long companyId) {
        var products = input.getProducts();
        if (products == null || products.isEmpty()) {
            log.warn("No products found in the invoice request.");
            return;
        }
        for (var product : products) {
            var existingProductStatistic = repository.findByProductName(product.getName());
            var productStatistic = ProductStatistic.from( product, companyId );
            if (existingProductStatistic.isPresent()) {
                log.info("Updating existing product statistic for product: {}", product.getName());
                var existingStatistic = existingProductStatistic.get();
                existingStatistic.setQuantity(existingStatistic.getQuantity() + productStatistic.getQuantity());
                existingStatistic.setTotalPriceHT(existingStatistic.getTotalPriceHT() + productStatistic.getTotalPriceHT());
                existingStatistic.setTotalPriceTTC(existingStatistic.getTotalPriceTTC() + productStatistic.getTotalPriceTTC());
                repository.save(existingStatistic);
            } else {
                log.info("Creating new product statistic for product: {}", product.getName());
                repository.save(productStatistic);
            }
        }
    }
}
