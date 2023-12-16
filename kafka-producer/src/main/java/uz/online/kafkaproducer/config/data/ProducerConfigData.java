package uz.online.kafkaproducer.config.data;

import lombok.Data;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "kafka.producer")
public class ProducerConfigData {
    private  String bootstrapServer;
    private  String keySerializer;
    private  String valueSerializer;

    public Map<String,Object> propsMap(){
        return Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,valueSerializer
        );
    }
}
