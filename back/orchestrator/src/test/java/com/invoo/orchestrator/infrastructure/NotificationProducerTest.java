package com.invoo.orchestrator.infrastructure;

import com.invoo.orchestrator.infrastructure.notification.NotificationProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.kafka.support.KafkaHeaders.KEY;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@ExtendWith(MockitoExtension.class)
public class NotificationProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Captor
    private ArgumentCaptor<Message<String>> messageCaptor;

    private NotificationProducer notificationProducer;

    @BeforeEach
    public void setUp() {
        notificationProducer = new NotificationProducer(kafkaTemplate);
    }

    @Test
    public void resetPasswordNotification_ShouldSendMessageWithCorrectHeadersAndPayload() {
        // Arrange
        String payload = "{\"email\":\"user@example.com\",\"token\":\"reset-token\"}";

        // Act
        notificationProducer.resetPasswordNotification(payload);

        // Assert
        verify(kafkaTemplate).send(messageCaptor.capture());
        Message<String> sentMessage = messageCaptor.getValue();
        assertEquals(payload, sentMessage.getPayload());
        assertEquals("reset-password-topic", sentMessage.getHeaders().get(TOPIC));
        assertEquals("reset-password", sentMessage.getHeaders().get(KEY));
    }

    @Test
    public void resetPasswordNotification_WithEmptyPayload_ShouldSendEmptyMessage() {
        // Arrange
        String payload = "";

        // Act
        notificationProducer.resetPasswordNotification(payload);

        // Assert
        verify(kafkaTemplate).send(messageCaptor.capture());
        Message<String> sentMessage = messageCaptor.getValue();
        assertEquals(payload, sentMessage.getPayload());
        assertEquals("reset-password-topic", sentMessage.getHeaders().get(TOPIC));
        assertEquals("reset-password", sentMessage.getHeaders().get(KEY));
    }

    @Test
    public void confirmAccount_ShouldSendMessageWithCorrectHeadersAndPayload() {
        // Arrange
        String payload = "{\"email\":\"user@example.com\",\"token\":\"confirmation-token\"}";

        // Act
        notificationProducer.confirmAccount(payload);

        // Assert
        verify(kafkaTemplate).send(messageCaptor.capture());
        Message<String> sentMessage = messageCaptor.getValue();
        assertEquals(payload, sentMessage.getPayload());
        assertEquals("confirm-account-topic", sentMessage.getHeaders().get(TOPIC));
        assertEquals("confirm-account", sentMessage.getHeaders().get(KEY));
    }

    @Test
    public void confirmAccount_WithEmptyPayload_ShouldSendEmptyMessage() {
        // Arrange
        String payload = "";

        // Act
        notificationProducer.confirmAccount(payload);

        // Assert
        verify(kafkaTemplate).send(messageCaptor.capture());
        Message<String> sentMessage = messageCaptor.getValue();
        assertEquals(payload, sentMessage.getPayload());
        assertEquals("confirm-account-topic", sentMessage.getHeaders().get(TOPIC));
        assertEquals("confirm-account", sentMessage.getHeaders().get(KEY));
    }
}