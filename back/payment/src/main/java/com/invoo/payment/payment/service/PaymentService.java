package com.invoo.payment.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invoo.global.payment.PaymentNotification;
import com.invoo.global.payment.PaymentRequestReceivedResponse;
import com.invoo.global.stripe.StripePaymentRequest;
import com.invoo.global.stripe.StripePaymentResponse;
import com.invoo.payment.notification.NotificationProducer;
import com.invoo.global.payment.dto.PaymentRequest;
import com.invoo.payment.payment.entity.Payment;
import com.invoo.payment.payment.entity.Status;
import com.invoo.payment.payment.entity.StripeRecord;
import com.invoo.payment.repository.IPaymentRequestRepository;
import com.invoo.payment.repository.IStripeRecordRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The type Payment service.
 */
@Service
@Slf4j
public class PaymentService {

    @Value("${api.stripe.service.url}")
    private String url;

    private final NotificationProducer notificationProducer;
    private final IPaymentRequestRepository paymentRequestRepository;
    private final IStripeRecordRepository stripeRecordRepository;

    private WebClient webClient;

    /**
     * Constructor to initialize the dependencies of the payment service.
     *
     * @param notificationProducer       Producer to send notifications.
     * @param paymentRequestRepository   Repository to manage payment requests.
     * @param stripeRecordRepository     Repository to manage Stripe records.
     */
    public PaymentService(NotificationProducer notificationProducer, IPaymentRequestRepository paymentRequestRepository, IStripeRecordRepository stripeRecordRepository) {
        this.notificationProducer = notificationProducer;
        this.paymentRequestRepository = paymentRequestRepository;
        this.stripeRecordRepository = stripeRecordRepository;
    }

    /**
     * Provides a Stripe payment session URL for a given payment.
     *
     * @param request The payment request containing the necessary information.
     * @return An HTTP response containing a success or error message.
     */
    public ResponseEntity<?> provideCheckoutUrlSession(PaymentRequest request) {

        StripePaymentRequest stripePaymentRequest = new StripePaymentRequest(
                (long) (request.amountInvoice() * 100),
                1L,
                request.invoiceName(),
                "EUR"
        );

        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();

        StripePaymentResponse session = webClient.post()
                .uri("/product/v1/checkout")
                .bodyValue(stripePaymentRequest)
                .retrieve()
                .bodyToMono(StripePaymentResponse.class)
                .block();

        Payment payment = new Payment();
        StripeRecord stripeRecord = new StripeRecord();

        assert session != null;
        if (session.status().equals("SUCCESS")) {
            stripeRecord.setSessionId(session.sessionId());
            stripeRecord.setUrlSession(session.sessionUrl());
            stripeRecord.setStatus(session.status());
            stripeRecord.setMessage(session.message());

            payment.setCompanyId(request.companyId());
            payment.setSenderId(request.senderId());
            payment.setCreatedAt(LocalDateTime.now());
            payment.setRecipientId(request.recipientId().toString());
            payment.setRecipientType(request.recipientType());
            payment.setRecipientName(request.recipientName());
            payment.setRecipientEmail(request.recipientEmail());
            payment.setStatus(Status.CREATED);
            payment.setInvoiceId(request.invoiceId());
            payment.setInvoiceName(request.invoiceName());
            payment.setAmount(request.amountInvoice());

            //save payment request to db
            var savedPayment = paymentRequestRepository.save(payment);

            // save stripe record to db
            stripeRecord.setPaymentId(savedPayment.getId());
            stripeRecordRepository.save(stripeRecord);
            // send notification
            sendNotification(request, session);

            return ResponseEntity.ok(savedPayment);

        } else {
            return ResponseEntity.badRequest().body(session.message());
        }
    }

    /**
     * Sends a payment notification after creating a Stripe session.
     *
     * @param request The payment request containing recipient information.
     * @param session The Stripe response containing the session URL and other details.
     */
    private void sendNotification(PaymentRequest request, StripePaymentResponse session) {
        var notification = new PaymentNotification(
                request.recipientName(),
                session.sessionUrl(),
                request.recipientEmail()
        );

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String message = objectMapper.writeValueAsString(notification);
            notificationProducer.sendNotification(message);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'envoi de la notification : " + e.getMessage());
        }
    }

    @Transactional
    public void updatePaymentStatus() {
        List<UUID> uuids = paymentRequestRepository.getAllPaymentsByStatusCreatedOrUnpaid();

        for (UUID uuid : uuids) {
            var stripeRecord = stripeRecordRepository.findByPaymentId(uuid);

            var status = getPaymentStatusFromStripe(stripeRecord.getSessionId());

            if (status != null) {
                if (status.equals(Status.PAID)) {
                    log.info("Payment status for session id = < {} > is paid", stripeRecord.getSessionId());
                    paymentRequestRepository.updatePaymentByIdAndStatus(uuid,Status.PAID);
                } else if (status.equals(Status.UNPAID)) {
                    log.info("Payment status for session id = < {} > is unpaid", stripeRecord.getSessionId());
                    paymentRequestRepository.updatePaymentByIdAndStatus(uuid,Status.UNPAID);
                }
            }
        }

    }


    private Status getPaymentStatusFromStripe(String sessionId) {
        log.info("Getting payment status for session id = < {} >", sessionId);
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
        var newStatus = webClient.get()
                .uri("/product/v1/status/" + sessionId)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        assert newStatus != null;
        if(newStatus.equals("paid")){
            return Status.PAID;
        } else if (newStatus.equals("unpaid")){
            return Status.UNPAID;
        } else {
            return null;
        }
    }

    public ResponseEntity<?> getAllPaymentRequestsByCompanyId(Long id) {
        List<Payment> paymentRequest = paymentRequestRepository.getAllPaymentRequestsByCompanyId(id);

        if (paymentRequest.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paymentRequest);
    }

    public ResponseEntity<?> getAllPaymentRequestsReceivedByCompanyId(Long id) {
        List<Payment> paymentRequest = paymentRequestRepository.getAllPaymentRequestsReceivedByCompanyId(id);
        if (paymentRequest.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        List<PaymentRequestReceivedResponse> paymentRequestReceivedResponse = paymentRequest.stream()
                .map(payment -> new PaymentRequestReceivedResponse(
                        payment.getId(),
                        payment.getCompanyId(),
                        payment.getSenderId(),
                        payment.getRecipientId(),
                        payment.getStatus().name(),
                        payment.getInvoiceId(),
                        payment.getInvoiceName(),
                        payment.getAmount().toString(),
                        payment.getCreatedAt(),
                        stripeRecordRepository.findByPaymentId(payment.getId()).getUrlSession()
                )).toList();
        return ResponseEntity.ok(paymentRequestReceivedResponse);
    }
}
