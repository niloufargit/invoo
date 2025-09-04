package com.invoo.payment.payment.entity;

import lombok.Getter;

@Getter
public enum Status {
    // Payment request created
    CREATED("CREATED"),
    // Creation of Payment request failed because of Stripe issues
    FAILED("FAILED"),
    // Payment request paid
    PAID("PAID"),
    // Payment request unpaid
    UNPAID("UNPAID");

    private final String status;

    Status(String status) {
        this.status = status;
    }
}
