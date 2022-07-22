package com.mclabs.learn.eventConsumer.v1.config;

import com.mclabs.learn.eventConsumer.EventConsumerException;
import com.mclabs.learn.eventConsumer.ServiceConsumer;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@NoArgsConstructor
public class SubscriberListenerBuilder {
    private String subscriberReference;
    private String hostName;
    private String vHost;
    private String userName;
    private String password;
    private ServiceConsumer serviceConsumer;
    private EventConsumerConfigBuilder eventConsumerConfigBuilder;

    public SubscriberListenerBuilder(String subscriberReference, String hostName, String vHost, String userName, String password, ServiceConsumer serviceConsumer) {
        this.subscriberReference = subscriberReference;
        this.hostName = hostName;
        this.vHost = vHost;
        this.userName = userName;
        this.password = password;
        this.serviceConsumer = serviceConsumer;
    }

    public static SubscriberListenerBuilder newBuilder() {
        return new SubscriberListenerBuilder();
    }

    public static SubscriberListener build(String subscriberReference, String hostName, String vHost, String userName, String password, ServiceConsumer serviceConsumer) {
        return new SubscriberListenerBuilder(subscriberReference, hostName, vHost, userName, password, serviceConsumer).build();
    }

    public SubscriberListenerBuilder withSubscribeReference(String subscriberReference) {
        this.subscriberReference = subscriberReference;
        return this;
    }
    public SubscriberListenerBuilder withHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }
    public SubscriberListenerBuilder withVHost(String vHost) {
        this.vHost = vHost;
        return this;
    }
    public SubscriberListenerBuilder withUserName(String userName) {
        this.userName = userName;
        return this;
    }
    public SubscriberListenerBuilder withPassword(String password) {
        this.password = password;
        return this;
    }
    public SubscriberListenerBuilder withServiceConsumer(ServiceConsumer serviceConsumer) {
        this.serviceConsumer = serviceConsumer;
        return this;
    }
    public void setEventConsumerConfigBuilder(EventConsumerConfigBuilder eventConsumerConfigBuilder) {
        this.eventConsumerConfigBuilder = eventConsumerConfigBuilder;
    }
    public EventConsumerConfigBuilder done() {
        eventConsumerConfigBuilder.addSubscriberListener(this.build());
        return eventConsumerConfigBuilder;
    }
    private SubscriberListener build() {
        if(StringUtils.isEmpty(subscriberReference)) {
            try {
                throw new EventConsumerException("Invalid subscriberReference or empty subscriberRefernce");
            } catch (EventConsumerException e) {
                throw new RuntimeException(e);
            }
        }
        // TODO: check empty for hostname, vhostm username, password, serviceConsumer == null
        SubscriberListener subscriberListener = new SubscriberListener();
        subscriberListener.setEventHostProfile(new EventHostProfile(hostName, vHost, userName, password));
        subscriberListener.setSubscriberReference(subscriberReference);
        subscriberListener.setServiceConsumer(serviceConsumer);
        return subscriberListener;
    }
}
