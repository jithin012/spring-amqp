package com.mclabs.learn.eventConsumer.v1;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class AMQPMessageToCloudEventTranslator {
    public static final String TRACEID = "traceid";
    public static final String ORIGINATOR = "originator";
    public static final String SOURCE = "source";

    public static final String CORRELATION_ID = "corelationid";
    public static final String REGISTRATION = "registration";
    public static final String DUMMY_ID = "dummyid";

    public CloudEvent  translateToCloudEvent(Message message) {
        CloudEvent cloudEvent = CloudEventBuilder.v1()
                .withId(message.getMessageProperties().getMessageId())
                .withType(message.getMessageProperties().getType())
                .withSource(URI.create(message.getMessageProperties().getHeaders().get(SOURCE).toString()))
                .withData(message.getMessageProperties().getContentType(), message.getBody())
//                .withExtension(TRACEID, message.getMessageProperties().getHeaders().containsKey(TRACEID))
//        ....withTime, withExtention(ORIGINATOR), withExtention(Registration), withExtenstion(CORRELATION_ID)
                .build();
        return cloudEvent;
    }

}
