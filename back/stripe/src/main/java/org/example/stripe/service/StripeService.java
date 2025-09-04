package org.example.stripe.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.invoo.global.stripe.StripePaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.stripe.dto.StripeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class StripeService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    public StripeResponse checkoutProducts(StripePaymentRequest stripePaymentRequest) {
        Stripe.apiKey = secretKey;
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(stripePaymentRequest.name())
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(stripePaymentRequest.currency() != null ? stripePaymentRequest.currency() : "EUR")
                        .setUnitAmount(stripePaymentRequest.amount())
                        .setProductData(productData)
                        .build();

        SessionCreateParams.LineItem lineItem =
                SessionCreateParams
                        .LineItem.builder()
                        .setQuantity(stripePaymentRequest.quantity())
                        .setPriceData(priceData)
                        .build();

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:4200/success")
                        .setCancelUrl("http://localhost:4200/cancel")
                        .addLineItem(lineItem)
                        .build();

        StripeResponse response;
        try {
            Session session = Session.create(params);
            response = StripeResponse
                    .builder()
                    .status("SUCCESS")
                    .message("Payment session created")
                    .sessionId(session.getId())
                    .sessionUrl(session.getUrl())
                    .build();
        } catch (StripeException e) {
            response = StripeResponse
                    .builder()
                    .status("FAILURE")
                    .message("Error creating payment session: " + e.getMessage())
                    .build();
        }
        return response;
    }

    public String pollPaymentStatusBySessionId(String sessionId) {
        Stripe.apiKey = secretKey;
        int maxAttempts = 5;
        int delay = 2000;
        log.info("Polling payment status of sessionId: {}", sessionId);
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                Session session = Session.retrieve(sessionId);
                String paymentStatus = session.getPaymentStatus();

                if ("paid".equals(paymentStatus)) {
                    return "paid";
                } else if ("unpaid".equals(paymentStatus)) {
                    return "unpaid";
                }

                Thread.sleep(delay);
            } catch (StripeException | InterruptedException e) {
               log.error("Error polling payment status of sessionId: {}, with error: {}", sessionId, e.getMessage());
            }
        }
        return "ERROR";
    }
}