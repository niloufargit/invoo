package com.invoo.orchestrator.infrastructure.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.KEY;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void resetPasswordNotification(String request) {
        log.info("Sending notification with body = < {} >", request);
        Message<String> message = MessageBuilder
                .withPayload(request)
                .setHeader(TOPIC, "reset-password-topic")
                .setHeader(KEY, "reset-password")
                .build();

        kafkaTemplate.send(message);
    }

    public void confirmAccount(String ConfirmAccount) {
        log.info("Sending notification with body = < {} >", ConfirmAccount);

        Message<String> message = MessageBuilder
                .withPayload(ConfirmAccount)
                .setHeader(TOPIC, "confirm-account-topic")
                .setHeader(KEY, "confirm-account")
                .build();

        kafkaTemplate.send(message);
    }
}
