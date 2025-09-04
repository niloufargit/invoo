package com.invoo.global.invoice;

public record Address (
        String streetNumber,
        String street,
        String city,
        String zipCode,
        String country
) {
}
