package com.invoo.payment.payment.controller;

import com.invoo.global.payment.dto.PaymentRequest;
import com.invoo.payment.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@Slf4j
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/request")
    public ResponseEntity<?> provideCheckoutUrlSession(@RequestBody PaymentRequest request) {
        return this.service.provideCheckoutUrlSession(request);
    }
    @GetMapping("/company/{id}")
    public ResponseEntity<?> getAllPaymentRequestsByCompanyId(@PathVariable Long id) {
        return this.service.getAllPaymentRequestsByCompanyId(id);
    }
    @GetMapping("/received/{id}")
    public ResponseEntity<?> getAllPaymentRequestsReceivedByCompanyId(@PathVariable Long id) {
        return this.service.getAllPaymentRequestsReceivedByCompanyId(id);
    }
}
