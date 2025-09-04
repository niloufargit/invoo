package com.invoo.global.stripe;

public record StripePaymentResponse(
        String status,
        String message,
        String sessionId,
        String sessionUrl
) {
}
