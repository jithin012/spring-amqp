package com.mclabs.learn.eventConsumer.config;

import com.mclabs.learn.eventConsumer.v1.config.SubscriberListener;

public interface EventConsumerConfigBuilder {


    public static EventConsumerConfigBuilder v1() {
        return (EventConsumerConfigBuilder) new com.mclabs.learn.eventConsumer.v1.config.EventConsumerConfigBuilder();
    }

    EventConsumerConfig build();
}
