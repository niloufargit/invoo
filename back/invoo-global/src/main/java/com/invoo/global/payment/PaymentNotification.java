package com.invoo.global.payment;

public record PaymentNotification (
        String recipientName,
        String sessionUrl,
        String recipientEmail
) {
}
