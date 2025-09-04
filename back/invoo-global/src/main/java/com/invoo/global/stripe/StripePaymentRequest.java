package com.invoo.global.stripe;

public record StripePaymentRequest (
    Long amount,
    Long quantity,
    String name,
    String currency
){}