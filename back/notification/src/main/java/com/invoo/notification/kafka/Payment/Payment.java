package com.invoo.notification.kafka.Payment;

public record Payment(
        String userFirstname,
        String userLastname,
        String userEmail) {
}
