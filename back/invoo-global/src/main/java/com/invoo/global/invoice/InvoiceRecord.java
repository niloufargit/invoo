package com.invoo.global.invoice;

import java.util.UUID;

public record InvoiceRecord (
        UUID id,
        Long invoiceId,
        Long supplierCompanyId,
        String customerId,
        String invoiceNumber,
        String invoiceTitle,
        double totalIncludingTax,
        double totalExcludingTax
){

}
