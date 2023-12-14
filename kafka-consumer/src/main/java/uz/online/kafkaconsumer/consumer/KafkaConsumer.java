package uz.online.kafkaconsumer.consumer;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaConsumer {

    @KafkaListener(id = "simpleID",topics = "simple")
    public void simpleListener(String message){
        log.info("Message receive(Xabarni qabul qilish): {}", message);
    }}
