package com.invoo.notification.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invoo.global.confirmaccount.ConfirmAccount;
import com.invoo.global.notification.ResetPasswordEmail;
import com.invoo.global.payment.PaymentNotification;
import com.invoo.notification.email.EmailService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

/**
 * The type Notification consumer.
 */
@Service
@Slf4j
public class NotificationConsumer {

    private final EmailService emailService;

    /**
     * Instantiates a new Notification consumer.
     *
     * @param emailService the email service
     */
    public NotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Consume forgotten password.
     *
     * @param value the value
     * @param topic the topic
     * @param key   the key
     */
    @KafkaListener(topics = "reset-password-topic")
    public void consumeForgottenPassword(
            String value,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_KEY) String key
    ) {

        ResetPasswordEmail resetPasswordEmail = parseMessage(value, ResetPasswordEmail.class);
        if (resetPasswordEmail == null) {
            log.error("Error parsing reset password message");
            return;
        }

        log.info( "Consuming the message from fpassword-topic Topic:: {}", resetPasswordEmail );
        try {
            emailService.sendResetPasswordEmail( resetPasswordEmail );
        } catch (MessagingException e) {
            log.error("Error sending reset password email: {}", e.getMessage());
        }

    }

    /**
     * Consume confirm account.
     *
     * @param value the value
     * @param topic the topic
     * @param key   the key
     */
    @KafkaListener(topics = "confirm-account-topic")
    public void consumeConfirmAccount(
            String value,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_KEY) String key
    ) {

        ConfirmAccount confirmAccount = parseMessage(value, ConfirmAccount.class);
        if (confirmAccount == null) {
            log.error("Error parsing reset password message");
            return;
        }

        log.info( "Consuming the message from fpassword-topic Topic:: {}", confirmAccount );

        try {
            emailService.sendConfirmAccountEmail( confirmAccount );
        } catch (MessagingException e) {
            log.error("Error sending reset password email: {}", e.getMessage());
        }

    }

    /**
     * Consume payment.
     *
     * @param value the value
     * @param topic the topic
     * @param key   the key
     */
    @KafkaListener(topics = "payment-topic")
    public void consumePayment(
            String value,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_KEY) String key
    ) {

        log.info( "Value:: {}", value );
        log.info( "Topic:: {}", topic );
        log.info( "KEY:: {}", key );

        PaymentNotification payment = parseMessage( value, PaymentNotification.class );
        if (payment == null) {
            log.error("Error parsing payment message");
            return;
        }
        log.info( "Consuming the message from payment-topic Topic:: {}",  payment );
        try {
            emailService.sendPaymentRequestEmail( payment );
        } catch (Exception e) {
            log.error( "Error parsing payment message: {}", e.getMessage() );
        }
    }

    private <T> T parseMessage(String value, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(value, clazz);
        } catch (Exception e) {
            log.error(format("Error parsing message: %s", e.getMessage()));
            return null;
        }
    }

}
