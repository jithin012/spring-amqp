package com.mclabs.learn.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Autowired
    RabbitmqConfigProperties rabbitmqConfigProperties;

    @Bean
    public ConnectionFactory amqpConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitmqConfigProperties.getHost());
        connectionFactory.setUsername(rabbitmqConfigProperties.getUsername());
        connectionFactory.setPassword(rabbitmqConfigProperties.getPassword());
//        connectionFactory.setVirtualHost(rabbitmqConfigProperties.getVirtualHost());
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(amqpConnectionFactory());
        return rabbitTemplate;
    }

    @Bean
    public Queue marketingQueue() {
        return new Queue("marketingQueue", false);
    }

    @Bean
    public Queue financeQueue() {
        return new Queue("financeQueue", false);
    }

    @Bean
    public Queue adminQueue() {
        return new Queue("adminQueue", false);
    }

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange("fanout-exchange");
    }

    @Bean
    Binding marketingBinding(Queue marketingQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(marketingQueue).to(exchange);
    }

    @Bean
    Binding financeBinding(Queue financeQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(financeQueue).to(exchange);
    }

    @Bean
    Binding adminBinding(Queue adminQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(adminQueue).to(exchange);
    }
}
