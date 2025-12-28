package com.example.task_tracker_email_sender.config;

import com.example.task_tracker_email_sender.events.UserCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public DefaultKafkaConsumerFactory<String, UserCreatedEvent> consumerFactory() {
        Map<String, Object> configProperties = new HashMap<>();
        configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "email-group");

        // This is related to a new client telemetry (observability) feature introduced in recent Kafka client versions.
        configProperties.put(ConsumerConfig.ENABLE_METRICS_PUSH_CONFIG, false);

        JacksonJsonDeserializer<UserCreatedEvent> deserializer = new JacksonJsonDeserializer<>(UserCreatedEvent.class);

        return new DefaultKafkaConsumerFactory<>(
                configProperties,
                new StringDeserializer(),
                deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserCreatedEvent> kafkaListenerContainerFactory(
            DefaultKafkaConsumerFactory<String, UserCreatedEvent> consumerFactory
    ) {
        var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, UserCreatedEvent>();
        containerFactory.setConsumerFactory(consumerFactory);
        containerFactory.setConcurrency(1);
        return containerFactory;
    }
}
