package com.mclabs.learn;

import com.mclabs.learn.config.RabbitmqConfigProperties;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class LearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnApplication.class, args);
		log.info("ref: https://www.javainuse.com/messaging/rabbitmq/exchange");
	}

//	@Bean
//	CommandLineRunner run() {
//		return args -> {
//			log.info(rabbitmqConfigProperties.toString());
//		};
//	}

}
