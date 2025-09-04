package com.invoo.global.payment;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentRequestReceivedResponse(
            UUID id,
            Long companyId,
            Long senderId,
            String recipientId,
            String status,
            Long invoiceId,
            String invoiceName,
            String amount,
            LocalDateTime createdAt,
            String sessionUrl
) {
}
