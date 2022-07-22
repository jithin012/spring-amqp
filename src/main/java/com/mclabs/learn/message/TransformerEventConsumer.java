package com.mclabs.learn.message;

import com.mclabs.learn.eventConsumer.EventConsumerRetriableException;
import com.mclabs.learn.eventConsumer.ServiceConsumer;
import io.cloudevents.CloudEvent;

public class TransformerEventConsumer implements ServiceConsumer {
    @Override
    public void onMessage(CloudEvent cloudEvent) throws EventConsumerRetriableException {
        // get the payload like
        byte[] eventData = cloudEvent.getData().toBytes();
        String message = new String(eventData);
        // Extract message from String data like
        //this.gson.fromJsob(inputPayloadString, InputPayload.class)
    }
}
