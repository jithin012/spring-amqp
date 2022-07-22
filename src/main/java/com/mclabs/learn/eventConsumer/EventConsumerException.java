package com.mclabs.learn.eventConsumer;

public class EventConsumerException extends Exception {
    public EventConsumerException(String message, Throwable e) {
        super(message, e);
    }

    public EventConsumerException(String message) {
        super(message);
    }
}
