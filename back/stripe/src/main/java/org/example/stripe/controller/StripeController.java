package org.example.stripe.controller;

import com.invoo.global.stripe.StripePaymentRequest;
import org.example.stripe.dto.StripeResponse;
import org.example.stripe.service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/v1")
@CrossOrigin
public class StripeController {

    private final StripeService stripeService;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(@RequestBody StripePaymentRequest stripePaymentRequest) {
        StripeResponse stripeResponse = stripeService.checkoutProducts(stripePaymentRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }
    @GetMapping("/status/{sessionId}")
    public ResponseEntity<String> paymentIntentStatus(@PathVariable String sessionId) {
        String status = stripeService.pollPaymentStatusBySessionId(sessionId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(status);
    }
}
