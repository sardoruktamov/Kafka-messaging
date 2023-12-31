package uz.online.kafkaproducer.service;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<Object,Object> kafkaTemplate;

    @Autowired
    private RoutingKafkaTemplate routingKafkaTemplate;

    @Autowired
    private ReplyingKafkaTemplate<Object,Object,String> replyingKafkaTemplate;

    public void send(String topic, String message){
        try {
            Message<String> m = new GenericMessage<>(message, Map.of(KafkaHeaders.TOPIC, topic));
            kafkaTemplate.send(m)
                    .thenAccept(result -> log.info("Message sent to kafka topic: {} ", result.getRecordMetadata().topic()));
        } catch (Exception e) {
            log.error("Error while sending message to {}, error: {}", topic,e.getMessage());
        }

    }

    public void sendByRouter(String topic, Object valuemessage){
        try {
            Message<Object> m = new GenericMessage<>(valuemessage, Map.of(KafkaHeaders.TOPIC, topic));
            routingKafkaTemplate.send(m)
                    .thenAccept(result -> log.info("Message was sent by routing to kafka topic: {} ", result.getRecordMetadata().topic()));
        } catch (Exception e) {
            log.error("Error while sending message by routing to {}, error: {}", topic,e.getMessage());
        }
    }

    public void sendAndRecieve(String topic, Object value){
        try {
            ProducerRecord<Object,Object> record = new ProducerRecord<>("simple",value);
            var response = replyingKafkaTemplate.sendAndReceive(record);
            var sendresult = response.getSendFuture().get();
            log.info("sent result: {}",sendresult.getRecordMetadata().topic());
            response.thenAccept(r -> {
                log.info("Reply message is received from {}, Response Data:{}",r.topic(),r.value());
            });
            var replyingValue = response.get();
        } catch (ExecutionException | InterruptedException e) {
            log.error("Error while sending and receive object via replying kafka template: {}", e.getMessage());
        }
    }
}
