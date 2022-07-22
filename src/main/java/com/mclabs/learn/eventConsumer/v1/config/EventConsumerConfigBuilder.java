package com.mclabs.learn.eventConsumer.v1.config;

import com.mclabs.learn.eventConsumer.EventConsumerException;
import com.mclabs.learn.eventConsumer.config.EventConsumerConfig;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Setter
public class EventConsumerConfigBuilder {
    private int concurrentConsumers = 6;
    private int maxConcurrentConsumers = 15;

    private int consecutiveActiveTrigger=1;
    private int startConsumerMinInterval=1000;
    private int stopConsumerMinInterval=30000;
    private int consecutiveIdleTrigger=1;
    private int prefetchCount=100;
    private long initialInterval=1000;
    private double multiplier=2;
    private long maxInterval=60000;
    private int maxAttempts=5;
    private boolean autoMode = false;
    private boolean externalRetry=false;

    private Set<SubscriberListener> subscriberListeners = new HashSet<SubscriberListener >();
    public void addSubscriberListener(SubscriberListener subscriberListener) {
        this.subscriberListeners.add(subscriberListener);
    }

    public EventConsumerConfigBuilder(int concurrentConsumers, int maxConcurrentConsumers, Set<SubscriberListener> subscriberListeners) {
        this.concurrentConsumers = concurrentConsumers;
        this.maxConcurrentConsumers = maxConcurrentConsumers;
        this.subscriberListeners = subscriberListeners;
    }

    public EventConsumerConfigBuilder withExternalRetry(boolean externalRetry) {
        this.externalRetry = externalRetry;
        return this;
    }
    //TODO: withAutoMode, withMaxAttempts, withMaxInterval, withMultiplier, withInitialInterval, withPrefetchCount, withConsecutiveIdleTriggerm withStopConsumerMinInterval, withStartConsumerMinInterval, withConsecutiveActiveTrigger, withMaxConcurrentConsumer, withConcurrentConsumer

    public EventConsumerConfig build() {
        if(subscriberListeners.isEmpty()) {
            try {
                throw new EventConsumerException("empty subscriber listener");
            } catch (EventConsumerException e) {
                throw new RuntimeException(e);
            }
        }
        EventConsumerConfigContext eventConsumerConfigContext = new EventConsumerConfigContext();
        eventConsumerConfigContext.setConcurrentConsumers(concurrentConsumers);
        eventConsumerConfigContext.setMaxConcurrentConsumer(maxConcurrentConsumers);
        eventConsumerConfigContext.setConsecutiveActiveTrigger(consecutiveActiveTrigger);
        eventConsumerConfigContext.setStartConsumerMinInterval(startConsumerMinInterval);
        //TODO: set till setSubscriberListener(sub...)
        return eventConsumerConfigContext;
    }
    public SubscriberListenerBuilder addSubscriberListener() {
        SubscriberListenerBuilder subscriberListenerBuilder = SubscriberListenerBuilder.newBuilder();
        subscriberListenerBuilder.setEventConsumerConfigBuilder(this);
        return subscriberListenerBuilder;
    }
}
