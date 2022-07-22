package com.mclabs.learn.eventConsumer;

public class EventConsumerRetriableException extends Exception {
    public EventConsumerRetriableException(String message, Throwable e) {
        super(message, e);
    }

    public EventConsumerRetriableException(String message) {
        super(message);
    }
}
