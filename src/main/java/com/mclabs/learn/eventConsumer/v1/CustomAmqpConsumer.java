package com.mclabs.learn.eventConsumer.v1;

import com.mclabs.learn.eventConsumer.EventConsumerException;
import com.mclabs.learn.eventConsumer.EventConsumerRetriableException;
import com.mclabs.learn.eventConsumer.ServiceConsumer;
import com.mclabs.learn.eventConsumer.config.EventConsumerConfig;
import com.mclabs.learn.eventConsumer.config.EventConsumerConfigBuilder;
import com.mclabs.learn.eventConsumer.v1.config.EventConsumerConfigContext;
import com.mclabs.learn.eventConsumer.v1.config.SubscriberListener;
import com.rabbitmq.client.Channel;
import io.cloudevents.CloudEvent;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class CustomAmqpConsumer {
    @Autowired
    public CustomAmqpConsumer(EventConsumerConfig eventConsumerConfig,
                              AMQPMessageToCloudEventTranslator amqpMessageToCloudEventTranslator) throws EventConsumerException {
        EventConsumerConfigContext eventConsumerConfigContext = (EventConsumerConfigContext) eventConsumerConfig;

        eventConsumerConfigContext.getSubscriberListeners().forEach(subscriberListener -> {
            log.info("creating connectors for queue={}, autoMode={}, externalRetry={}", subscriberListener.getSubscriberReference(), eventConsumerConfigContext.isAutoMode(), eventConsumerConfigContext.isExternalRetry());
            SimpleMessageListenerContainer container = null;
            //                container = getContainerInstance(subscriberListener, eventConsumerConfigContext);
            container = new SimpleMessageListenerContainer(connectionFactory(subscriberListener, eventConsumerConfigContext));
            container.setMessageListener(new ChannelAwareMessageListener() {
                @Override
                public void onMessage(Message message, Channel channel) throws Exception {
                    if (eventConsumerConfigContext.isAutoMode()) {
                        processMessageAuto(message, channel, amqpMessageToCloudEventTranslator, subscriberListener.getServiceConsumer());
                    } else {
                        processMessage(message, channel, amqpMessageToCloudEventTranslator, subscriberListener.getServiceConsumer());
                    }
                }
            });
            container.setConcurrentConsumers((eventConsumerConfigContext.getConcurrentConsumers()));
            container.setMaxConcurrentConsumers(eventConsumerConfigContext.getMaxConcurrentConsumer());
            container.setConsecutiveActiveTrigger(eventConsumerConfigContext.getConsecutiveActiveTrigger());
            container.setStartConsumerMinInterval(eventConsumerConfigContext.getStartConsumerMinInterval());
            container.setStopConsumerMinInterval(eventConsumerConfigContext.getStopConsumerMinInterval());
            container.setConsecutiveIdleTrigger(eventConsumerConfigContext.getConsecutiveIdleTrigger());
            container.setPrefetchCount(eventConsumerConfigContext.getPrefetchCount());
            container.setQueueNames(subscriberListener.getSubscriberReference());
            container.setChannelTransacted(true);
            if(eventConsumerConfigContext.isAutoMode()) {
                container.setAcknowledgeMode(AcknowledgeMode.AUTO);
                if (eventConsumerConfigContext.isExternalRetry()) {
                    container.setAdviceChain(new Advice[] {
                            org.springframework.amqp.rabbit.config.RetryInterceptorBuilder
                                    .stateful()
                                    .maxAttempts(eventConsumerConfigContext.getMaxAttempt())
                                    .backOffOptions(eventConsumerConfigContext.getInitialInterval(), eventConsumerConfigContext.getMultiplier(), eventConsumerConfigContext.getMaxInterval())
                                    .build()
                    });
                } else {
                    container.setAdviceChain(new Advice[] {
                            org.springframework.amqp.rabbit.config.RetryInterceptorBuilder
                                    .stateless()
                                    .maxAttempts(eventConsumerConfigContext.getMaxAttempt())
                                    .backOffOptions(eventConsumerConfigContext.getInitialInterval(), eventConsumerConfigContext.getMultiplier(), eventConsumerConfigContext.getMaxInterval())
                                    .build()
                    });
                }
            }  else {
                container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
            }
            container.afterPropertiesSet();
            container.start();
            log.info("container started for the queue = {}", subscriberListener.getSubscriberReference());
        });
    }

    protected void processMessage(Message message, Channel channel, AMQPMessageToCloudEventTranslator amqpMessageToCloudEventTranslator, ServiceConsumer serviceConsumer) {
        Long startTime = System.currentTimeMillis();
        try {
            CloudEvent cloudEvent = amqpMessageToCloudEventTranslator.translateToCloudEvent(message);
            serviceConsumer.onMessage(cloudEvent);
            if (channel.isOpen()) {
                try {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    log.info("successful ack");
                } catch (IOException e) {
                    log.info("Error while performing ack");
                }
            } else {
                log.error("channel closed");
            }
        } catch (EventConsumerRetriableException e) {
            if (channel.isOpen()) {
                try {
                   channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                } catch (IOException ex) {
                    log.info("Error while performing ack");
                }
            }
            throw new RuntimeException(e);
        }
    }

    protected void processMessageAuto(Message message, Channel channel, AMQPMessageToCloudEventTranslator amqpMessageToCloudEventTranslator, ServiceConsumer serviceConsumer) {
        Long startTime = System.currentTimeMillis();
        try {
            CloudEvent cloudEvent = amqpMessageToCloudEventTranslator.translateToCloudEvent(message);
            serviceConsumer.onMessage(cloudEvent);
        } catch (EventConsumerRetriableException e) {
            log.error("error in omMessage");
            throw new RuntimeException(e);
        }  catch (Exception exception) {
            log.error("error ...");
        }
    }

    protected CachingConnectionFactory connectionFactory(SubscriberListener subscriberListener, EventConsumerConfigContext eventConsumerConfigContext) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(subscriberListener.getEventHostProfile().getHostname());
        connectionFactory.setVirtualHost(subscriberListener.getEventHostProfile().getVHost());
        connectionFactory.setUsername(subscriberListener.getEventHostProfile().getUsername());
        connectionFactory.setPassword(subscriberListener.getEventHostProfile().getPassword());
        return connectionFactory;
    }
}
