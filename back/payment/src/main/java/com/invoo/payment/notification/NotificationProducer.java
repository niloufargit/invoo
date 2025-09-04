package com.invoo.payment.notification;

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

    public void sendNotification(String request) {
        log.info("Sending notification with body = < {} >", request);
        Message<String> message = MessageBuilder
                .withPayload(request)
                .setHeader(TOPIC, "payment-topic")
                .setHeader( KEY, "payment")
                .build();

        kafkaTemplate.send(message);
    }
}
