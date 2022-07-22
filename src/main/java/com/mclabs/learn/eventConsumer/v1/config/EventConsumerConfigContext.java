package com.mclabs.learn.eventConsumer.v1.config;

import com.mclabs.learn.eventConsumer.config.EventConsumerConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class EventConsumerConfigContext implements EventConsumerConfig {
    private int concurrentConsumers = 6;
    private int maxConcurrentConsumer = 15;

    private int consecutiveActiveTrigger=1;
    private int startConsumerMinInterval=1000;
    private int stopConsumerMinInterval=30000;
    private int consecutiveIdleTrigger=1;
    private int prefetchCount=100;
    private long initialInterval=1000;
    private double multiplier=2;
    private long maxInterval=60000;
    private int maxAttempt=5;
    private boolean autoMode = false;
    private boolean externalRetry=false;

    private Set<SubscriberListener> subscriberListeners = new HashSet<SubscriberListener >();
}
