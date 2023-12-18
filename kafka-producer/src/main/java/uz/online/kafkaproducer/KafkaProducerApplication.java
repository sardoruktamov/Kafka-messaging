package uz.online.kafkaproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import uz.online.kafkaproducer.service.KafkaProducerService;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "uz.online")
public class KafkaProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	@Autowired
	private KafkaAdmin kafkaAdmin;

	@Bean
	public ApplicationRunner runner(KafkaProducerService kafkaProducerService){
		return args -> {
			kafkaAdmin.createOrModifyTopics(TopicBuilder.name("runtime-topic").build());
			kafkaProducerService.send("simple", "Signal - topic uchun Xabar");
			kafkaProducerService.sendByRouter("simple", "Signal - Router yordamida topici uchun Xabar".getBytes());
			kafkaProducerService.sendByRouter("first", "First-signal - Router yordamida topici uchun Xabar");
		};

	}

}
