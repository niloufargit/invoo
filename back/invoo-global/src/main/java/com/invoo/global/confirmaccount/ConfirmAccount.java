package com.invoo.global.confirmaccount;

public record ConfirmAccount(
        String email,
        String firstname,
        String lastname,
        String token
) {
}
