package uz.online.kafkaproducer;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uz.online.kafkaproducer.service.KafkaProducerService;

@SpringBootApplication
public class KafkaProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(KafkaProducerService kafkaProducerService){
		return args -> {
			kafkaProducerService.send("simple", "Signal - topici uchun Xabar");
		};

	}

}
