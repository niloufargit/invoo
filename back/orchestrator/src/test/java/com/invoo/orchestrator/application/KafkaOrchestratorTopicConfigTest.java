package com.invoo.orchestrator.application;

import com.invoo.orchestrator.application.config.kafka.KafkaOrchestratorTopicConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.config.TopicBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class KafkaOrchestratorTopicConfigTest {

    private final KafkaOrchestratorTopicConfig topicConfig = new KafkaOrchestratorTopicConfig();

    @Test
    void resetPasswordTopicShouldBeCreatedWithCorrectName() {
        NewTopic topic = topicConfig.resetPasswordTopic();

        assertNotNull(topic);
        assertEquals("reset-password-topic", topic.name());
    }

    @Test
    void confirmAccountTopicShouldBeCreatedWithCorrectName() {
        NewTopic topic = topicConfig.confirmAccountTopic();

        assertNotNull(topic);
        assertEquals("confirm-account-topic", topic.name());
    }


    @Test
    void topicShouldHaveDefaultPartitionsAndReplicationFactorWhenNotSpecified() {
        NewTopic topic = TopicBuilder.name("default-topic").build();

        assertNotNull(topic);
        assertEquals(-1, topic.numPartitions()); // Par défaut, 1 partition
        assertEquals(-1, topic.replicationFactor()); // Par défaut, facteur de réplication 1
    }

}
