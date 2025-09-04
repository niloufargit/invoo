package com.invoo.global.statistic;

import java.util.UUID;

public record ProductStatisticDTO (
        UUID id,
        Long companyId,
        String productName,
        int quantity,
        double totalPriceHT,
        double totalPriceTTC,
        int pcQuantity,
        double pcTotalPriceHT,
        double pcTotalPriceTTC
) {
}
