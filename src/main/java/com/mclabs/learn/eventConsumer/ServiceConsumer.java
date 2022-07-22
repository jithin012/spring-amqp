package com.mclabs.learn.eventConsumer;

import io.cloudevents.CloudEvent;

public interface ServiceConsumer {
    void onMessage(CloudEvent cloudEvent) throws EventConsumerRetriableException;
}
