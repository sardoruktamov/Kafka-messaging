package uz.online.kafkaproducer.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic simpleTopic(){
        return TopicBuilder.name("simple")
                .partitions(2)
                .replicas(1)
                .build();
    }

    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String,Object> props=new HashMap<>();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
//        props.put()
        return new KafkaAdmin(props);
    }

    @Bean
    public KafkaAdmin.NewTopics kafkaTopics(){
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("first")
                        .build(),
                TopicBuilder.name("second")
                        .build()
        );
    }

}
