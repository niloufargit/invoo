package com.invoo.payment;

import com.invoo.global.stripe.StripePaymentResponse;
import com.invoo.payment.notification.NotificationProducer;
import com.invoo.global.payment.dto.PaymentRequest;
import com.invoo.global.payment.dto.RecipientType;
import com.invoo.payment.payment.service.PaymentService;
import com.invoo.payment.repository.IPaymentRequestRepository;
import com.invoo.payment.repository.IStripeRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private NotificationProducer notificationProducer;

    @Mock
    private IPaymentRequestRepository IPaymentRequestRepository;

    @Mock
    private IStripeRecordRepository IStripeRecordRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void testSendNotification() {
        // This test verifies that the notification producer is called with the correct message
        // when a payment notification is sent

        // Arrange
        doNothing().when(notificationProducer).sendNotification(anyString());

        // Create a payment request
        PaymentRequest request = new PaymentRequest(
                1L,
                "Invoice #1",
                100D,
                2L,
                1L,
                RecipientType.USER,
                "John Doe",
                "john.doe@example.com",
                3L,
                "john.doe@external.com"
        );

        // Create a stripe response
        StripePaymentResponse stripeResponse = new StripePaymentResponse(
                "SUCCESS",
                "Payment session created successfully",
                "sess_123456",
                "https://stripe.com/checkout/sess_123456"
        );

        // Act - call the private method using reflection
        try {
            Method sendNotificationMethod = PaymentService.class.getDeclaredMethod(
                    "sendNotification", PaymentRequest.class, StripePaymentResponse.class);
            sendNotificationMethod.setAccessible(true);
            sendNotificationMethod.invoke(paymentService, request, stripeResponse);
        } catch (Exception e) {
            fail("Failed to invoke sendNotification method: " + e.getMessage());
        }

        // Assert
        // Verify that the notification producer was called
        verify(notificationProducer).sendNotification(anyString());

        // Capture the message that was sent
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(notificationProducer).sendNotification(messageCaptor.capture());

        // Verify that the message contains the expected data
        String message = messageCaptor.getValue();
        assertTrue(message.contains(request.recipientName()), "Message should contain recipient name");
        assertTrue(message.contains(request.recipientEmail()), "Message should contain recipient email");
        assertTrue(message.contains(stripeResponse.sessionUrl()), "Message should contain session URL");
    }

    @Test
    void testSendPaymentNotification() {
        // This test verifies that the notification producer is called with the correct message
        // when a payment notification is sent using the sendPaymentNotification method

        // Arrange
        doNothing().when(notificationProducer).sendNotification(anyString());

        // Create a payment request
        PaymentRequest request = new PaymentRequest(
                1L,
                "Invoice #1",
                100D,
                2L,
                1L,
                RecipientType.USER,
                "John Doe",
                "john.doe@example.com",
                3L,
                "john.doe@external.com"
        );

        // Act - call the private method using reflection
        String result = null;
        try {
            Method sendPaymentNotificationMethod = PaymentService.class.getDeclaredMethod(
                    "sendPaymentNotification", PaymentRequest.class);
            sendPaymentNotificationMethod.setAccessible(true);
            result = (String) sendPaymentNotificationMethod.invoke(paymentService, request);
        } catch (Exception e) {
            fail("Failed to invoke sendPaymentNotification method: " + e.getMessage());
        }

        // Assert
        // Verify that the notification producer was called
        verify(notificationProducer).sendNotification(anyString());

        // Verify that the result contains the expected error message
        assertNotNull(result, "Result should not be null");
        // The result should contain an error message about LocalDateTime not being supported
        assertTrue(result.contains("Error parsing payment message"), "Result should contain error message");
        assertTrue(result.contains("LocalDateTime"), "Result should mention LocalDateTime");

        // Capture the message that was sent
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(notificationProducer).sendNotification(messageCaptor.capture());

        // Verify that the message contains the expected data
        String message = messageCaptor.getValue();
        assertTrue(message.contains(request.recipientEmail()), "Message should contain recipient email");
    }

    @Test
    void sendNotificationThrowsException() {
        // Arrange
        PaymentRequest request = new PaymentRequest(
                1L,
                "Invoice #1",
                100D,
                2L,
                1L,
                RecipientType.USER,
                "John Doe",
                "john.doe@example.com",
                3L,
                "john.doe@external.com"
        );

        StripePaymentResponse stripeResponse = new StripePaymentResponse(
                "SUCCESS", "Payment session created successfully",
                "sess_123456", "https://stripe.com/checkout/sess_123456"
        );

        doThrow(new RuntimeException("Notification service unavailable"))
            .when(notificationProducer).sendNotification(anyString());

        // Act & Assert
        Exception exception = assertThrows(InvocationTargetException.class, () -> {
            Method sendNotificationMethod = PaymentService.class.getDeclaredMethod(
                    "sendNotification", PaymentRequest.class, StripePaymentResponse.class);
            sendNotificationMethod.setAccessible(true);
            sendNotificationMethod.invoke(paymentService, request, stripeResponse);
        });

        assertTrue(exception.getCause().getMessage().contains("Erreur lors de l'envoi de la notification"));
    }

    @Test
    void sendPaymentNotificationHandlesSerializationException() throws Exception {
        // Arrange
        PaymentRequest request = new PaymentRequest(
                1L,
                "Invoice #1",
                100D,
                2L,
                1L,
                RecipientType.USER,
                "John Doe",
                "john.doe@example.com",
                3L,
                "john.doe@external.com"
        );

        doThrow(new IllegalArgumentException("Custom serialization error"))
            .when(notificationProducer).sendNotification(anyString());

        // Act
        Method sendPaymentNotificationMethod = PaymentService.class.getDeclaredMethod(
                "sendPaymentNotification", PaymentRequest.class);
        sendPaymentNotificationMethod.setAccessible(true);
        String result = (String) sendPaymentNotificationMethod.invoke(paymentService, request);

        // Assert
        assertTrue(result.contains("Error parsing payment message"));
    }

}
