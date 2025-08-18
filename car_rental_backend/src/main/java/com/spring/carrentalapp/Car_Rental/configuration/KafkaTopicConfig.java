package com.spring.carrentalapp.Car_Rental.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
	
	@Bean
    public NewTopic userRegistrationsTopic() {
        return TopicBuilder.name("user-registrations")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
