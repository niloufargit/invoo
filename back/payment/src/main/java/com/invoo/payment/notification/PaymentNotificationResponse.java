package com.invoo.payment.notification;

import java.time.LocalDateTime;

public record PaymentNotificationResponse(
        String status,
        LocalDateTime sentAt
){
}
