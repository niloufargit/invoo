package com.invoo.orchestrator.client.payment.controller;

import com.invoo.global.payment.dto.PaymentRequest;
import com.invoo.orchestrator.client.payment.service.PaymentServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentServiceClient paymentService;

    @PostMapping("/request")
    public ResponseEntity<?> requestPayment(@RequestBody PaymentRequest request) {
        return this.paymentService.provideCheckoutUrlSession(request);
    }
    @GetMapping("/company/{id}")
    public ResponseEntity<?> getAllPaymentRequestsByCompanyId( @PathVariable Long id) {
        return this.paymentService.getAllPaymentRequestsByCompanyId(id);
    }
    @GetMapping("/received/{id}")
    public ResponseEntity<?> getAllPaymentRequestsReceivedByCompanyId(@PathVariable Long id) {
        return this.paymentService.getAllPaymentRequestsReceivedByCompanyId(id);
    }


}
