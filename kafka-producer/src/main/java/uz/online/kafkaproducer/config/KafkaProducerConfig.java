package uz.online.kafkaproducer.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.serializer.JsonSerializer;
import uz.online.kafkaproducer.config.data.ProducerConfigData;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final ProducerConfigData producerConfigData;

    @Bean
    @Primary
    public KafkaTemplate<Object,Object> stringKafkaProducer() throws ClassNotFoundException {
        var kafkaTemplate = new KafkaTemplate<>(defaultKafkaProducerFactory());
        kafkaTemplate.setDefaultTopic("simple");
        return kafkaTemplate;
    };

    @Bean
    public RoutingKafkaTemplate routingKafkaTemplate() throws ClassNotFoundException{
        var routingKafkaTemplateProducerFactory  = new LinkedHashMap<Pattern, ProducerFactory<Object, Object>>();

        routingKafkaTemplateProducerFactory.put(Pattern.compile("simple"), byteValueProducerFactory());
        routingKafkaTemplateProducerFactory.put(Pattern.compile("users"), byteValueProducerFactory());
        routingKafkaTemplateProducerFactory.put(Pattern.compile(".+"), defaultKafkaProducerFactory());

        return new RoutingKafkaTemplate(routingKafkaTemplateProducerFactory);
    };
    @Bean
    public Map<String,Object> producerConfigs() throws ClassNotFoundException{
        return producerConfigData.propsMap();
    }

    @Bean
    public ProducerFactory<Object,Object> defaultKafkaProducerFactory() throws ClassNotFoundException {
        var defaultProducerFactory = new DefaultKafkaProducerFactory<Object,Object>(
                producerConfigs(), () -> new JsonSerializer<>(), () -> new JsonSerializer<>());
        defaultProducerFactory.setProducerPerThread(true);
        return defaultProducerFactory;
    }


    @Bean
    public ProducerFactory<Object,Object> byteValueProducerFactory() throws ClassNotFoundException {
        var byteValueKafkaConfig = new HashMap<>(producerConfigs());
        byteValueKafkaConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        return new DefaultKafkaProducerFactory<>(byteValueKafkaConfig);
    }
}
