package com.mclabs.learn;

import com.mclabs.learn.aopExample.PassengerDao;
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
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
@Slf4j
public class LearnApplication {

	public static void main(String[] args) {
//		SpringApplication.run(LearnApplication.class, args);
//		log.info("ref: https://www.javainuse.com/messaging/rabbitmq/exchange");
		ClassPathXmlApplicationContext context =
				new ClassPathXmlApplicationContext("aopExample/exampleaop.xml");

		PassengerDao passengerDao = (PassengerDao)context.getBean("passengerDao");
		System.out.println(passengerDao.getPassenger(1));

		context.close();
	}

//	@Bean
//	CommandLineRunner run() {
//		return args -> {
//			log.info(rabbitmqConfigProperties.toString());
//		};
//	}

}
