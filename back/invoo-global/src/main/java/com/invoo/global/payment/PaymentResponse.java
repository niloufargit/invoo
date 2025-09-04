package com.invoo.global.payment;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponse(
UUID id,
Long companyId,
String recipientId,
String recipientType,
String recipientName,
String recipientEmail,
String status,
Long invoiceId,
String invoiceName,
String amount,
LocalDateTime createdAt
) {
}
