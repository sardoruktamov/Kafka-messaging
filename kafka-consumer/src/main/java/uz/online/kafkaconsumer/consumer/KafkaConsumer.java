package uz.online.kafkaconsumer.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class KafkaConsumer {

    private final KafkaTemplate<String,String> kafkaTemplate;

    @KafkaListener(id = "simpleID",topics = "simple")
    public void simpleListener(String message){
        kafkaTemplate.send("second", "Replying message from consumer");
        log.info("Message receive(Xabarni qabul qilish): {}", message);
    }}
