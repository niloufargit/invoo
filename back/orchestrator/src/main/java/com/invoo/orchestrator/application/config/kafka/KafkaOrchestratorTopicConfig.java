package com.invoo.orchestrator.application.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaOrchestratorTopicConfig {

    @Bean
    public NewTopic resetPasswordTopic() {
        return TopicBuilder.name( "reset-password-topic" ).build();
    }

    @Bean
    public NewTopic confirmAccountTopic() {
        return TopicBuilder.name( "confirm-account-topic" ).build();
    }

}
