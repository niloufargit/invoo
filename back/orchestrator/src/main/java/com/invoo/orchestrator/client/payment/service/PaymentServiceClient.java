package com.invoo.orchestrator.client.payment.service;

import com.invoo.global.payment.PaymentRequestReceivedResponse;
import com.invoo.global.payment.PaymentResponse;
import com.invoo.global.payment.dto.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

public class PaymentServiceClient {

    @Value("${api.payment.service.url}")
    private String url;

    private static final Logger log = LoggerFactory.getLogger( PaymentServiceClient.class);

    private WebClient client;

    public PaymentServiceClient(WebClient client) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public ResponseEntity<?> provideCheckoutUrlSession(PaymentRequest request) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
        log.info("Requesting payment service for checkout URL session");
        var response = client.post()
                .uri("/api/v1/payment/request")
                .bodyValue(request)
                .retrieve()
                .bodyToMono( PaymentResponse.class)
                .log()
                .block();

        assert response != null;
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getAllPaymentRequestsByCompanyId(Long id) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
        log.info("Fetching all payment requests for company ID: {}", id);
        log.info("Payment service URL: {}", client);
        var res =  client.get()
                .uri("/api/v1/payment/company/{id}", id)
                .retrieve()
                .bodyToFlux(PaymentResponse.class)
                .collectList()
                .block();
        log.info("Payment requests fetched successfully for company ID: {}", res);
        return ResponseEntity.ok(res);
    }

    public ResponseEntity<?> getAllPaymentRequestsReceivedByCompanyId(Long id) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .build();
        log.info("Fetching all payment requests received by company ID: {}", id);
        var res = client.get()
                .uri("/api/v1/payment/received/{id}", id)
                .retrieve()
                .bodyToFlux(PaymentRequestReceivedResponse.class)
                .collectList()
                .block();
        log.info("Payment requests received fetched successfully for company ID: {}", res);
        return ResponseEntity.ok(res);

    }
}
