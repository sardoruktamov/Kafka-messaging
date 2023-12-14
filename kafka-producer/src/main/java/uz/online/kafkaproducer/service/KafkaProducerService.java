package uz.online.kafkaproducer.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void send(String topic, String message){
        try {
            kafkaTemplate.send(topic,message)
                    .thenAccept(result -> log.info("Message sent to kafka topic: {} ", result.getRecordMetadata().topic()));
        } catch (Exception e) {
            log.error("Error while sending message to {}, error: {}", topic,e.getMessage());
        }

    }
}
