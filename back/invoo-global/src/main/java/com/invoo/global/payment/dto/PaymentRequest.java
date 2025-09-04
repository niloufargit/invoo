package com.invoo.global.payment.dto;



public record PaymentRequest(
        Long invoiceId,
        String invoiceName,
        Double amountInvoice,
        Long senderId,
        Long recipientId,
        RecipientType recipientType,
        String recipientName,
        String recipientEmail,
        Long companyId,
        String recipientExternalEmail
) {
}