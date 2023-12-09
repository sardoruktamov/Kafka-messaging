package uz.online.kafkaproducer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic simpleTopic(){
        return TopicBuilder.name("simple")
                .partitions(2)
                .replicas(2)
                .build();
    }
}
