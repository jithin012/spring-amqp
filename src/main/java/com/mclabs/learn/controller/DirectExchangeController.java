package com.mclabs.learn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//eg: http://localhost:8090/exchange/sendToExchange?exchangeName=direct-exchange&routingKey=marketing&messageData=HelloWorldJavaInUse
//    http://localhost:8090/exchange/sendToFanoutExchange?exchangeName=fanout-exchange&messageData=HelloWorldJavaInUse
@RestController
@RequestMapping("/exchange/")
@Slf4j
public class DirectExchangeController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("sendToDirectExchange")
    public String producer(
            @RequestParam("exchangeName") String exchange,
            @RequestParam("routingKey") String routingKey,
            @RequestParam("messageData") String messageData
    ) {
        rabbitTemplate.convertAndSend(exchange, routingKey, messageData);
        return "Message sent to RabbitMQ Successfully!!!";
    }

    @GetMapping("sendToFanoutExchange")
    public String Fanoutproducer(
            @RequestParam("exchangeName") String exchange,
            @RequestParam("messageData") String messageData
    ) {
        rabbitTemplate.convertAndSend(exchange, "", messageData);
        return "Message sent to RabbitMQ Successfully!!!";
    }
}
