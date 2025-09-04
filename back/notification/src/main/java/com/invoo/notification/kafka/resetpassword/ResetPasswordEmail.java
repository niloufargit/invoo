package com.invoo.notification.kafka.resetpassword;

public record ResetPasswordEmail(
        String firstname,
        String lastname,
        String email,
        String resetPasswordLink
) {}
