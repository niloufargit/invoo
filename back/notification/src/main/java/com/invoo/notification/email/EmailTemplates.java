package com.invoo.notification.email;

import lombok.Getter;

public enum EmailTemplates {

    PAYMENT_REQUEST("payment-request.html", "Payment request received"),
    ACTIVATE_ACCOUNT("activate_account", "Activate your account"),
    RESET_PASSWORD("reset_password", "Reset your password");

    @Getter
    private final String template;
    @Getter
    private final String subject;


    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }

}
