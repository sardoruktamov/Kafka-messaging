package uz.online.kafkaproducer.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import uz.online.kafkaproducer.config.data.ProducerConfigData;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final ProducerConfigData producerConfigData;

    @Bean
    public KafkaTemplate<String,String> stringKafkaProducer(){
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(defaultKafkaProducerFactory());
        kafkaTemplate.setDefaultTopic("simple");
        kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
            @Override
            public void onSuccess(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata) {
                ProducerListener.super.onSuccess(producerRecord, recordMetadata);
            }

            @Override
            public void onError(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata, Exception exception) {
                ProducerListener.super.onError(producerRecord, recordMetadata, exception);
            }
        });
        return kafkaTemplate;
    };

    @Bean
    public Map<String,Object> producerKonfigs(){
        return producerConfigData.propsMap();
    }

    @Bean
    public ProducerFactory<String,String> defaultKafkaProducerFactory(){
        return new DefaultKafkaProducerFactory<>(producerKonfigs());
    }
}
