package com.invoo.invoice.statistics.products.domaine;

import com.invoo.invoice.statistics.products.ProductStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductsStatisticsRepository extends JpaRepository<ProductStatistic, UUID> {


    @Query("SELECT ps FROM ProductStatistic ps ORDER BY ps.quantity DESC LIMIT 10")
    List<ProductStatistic> getProductStatisticTop10();


    @Query("SELECT ps FROM ProductStatistic ps WHERE ps.productName = ?1")
    Optional<ProductStatistic> findByProductName(String productName);

    @Query("SELECT ps FROM ProductStatistic ps WHERE ps.companyId = ?1 ORDER BY ps.quantity DESC LIMIT 10")
    List<ProductStatistic> getProductStatisticTop10WithCompany(Long companyId);
}
