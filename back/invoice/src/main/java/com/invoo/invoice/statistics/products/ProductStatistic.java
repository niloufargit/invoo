package com.invoo.invoice.statistics.products;

import com.invoo.invoice.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long companyId;
    private String productName;
    private int quantity;
    private double totalPriceHT;
    private double totalPriceTTC;


    public static ProductStatistic from(Product product, Long companyId) {
        return new ProductStatistic(
                null, // id will be generated
                companyId,
                product.getName(),
                product.getQuantity(),
                product.getHtPrice() * product.getQuantity()
                , (product.getHtPrice() * product.getQuantity()) * (1 + (double) product.getVatRate() / 100)  // totalPriceHT
        );
    }

}
