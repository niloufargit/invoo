package com.invoo.payment.schedule;

import com.invoo.payment.payment.service.PaymentService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private final PaymentService paymentService;

    public ScheduledTasks(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @Scheduled(fixedRateString = "PT159H")
    public void pollingPaymentStatus() {
        paymentService.updatePaymentStatus();
    }
}
