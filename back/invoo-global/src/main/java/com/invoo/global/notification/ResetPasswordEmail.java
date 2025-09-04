package com.invoo.global.notification;

public record ResetPasswordEmail(
        String firstname,
        String lastname,
        String email,
        String resetPasswordLink
) {}
