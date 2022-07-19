package com.mclabs.learn.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @RabbitListener(queues = "adminQueue")
    public void adminQueuePoller(Message message){
        String messagePayload = new String(message.getBody());
        log.info("message poller starts at adminQueue with {}", messagePayload);

        // TODO: process messagePayload
    }

    @RabbitListener(queues = "financeQueue")
    public void financeQueuePoller(Message message){
        String messagePayload = new String(message.getBody());
        log.info("message poller starts at financeQueue with {}", messagePayload);

        // TODO: process messagePayload
    }

    @RabbitListener(queues = "marketingQueue")
    public void marketingQueuePoller(Message message){
        String messagePayload = new String(message.getBody());
        log.info("message poller starts at marketingQueue with {}", messagePayload);

        // TODO: process messagePayload
    }
}
