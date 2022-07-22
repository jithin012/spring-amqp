package com.mclabs.learn.eventConsumer.v1.config;


import com.mclabs.learn.eventConsumer.ServiceConsumer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriberListener {
    private EventHostProfile eventHostProfile;
    private String subscriberReference;
    private ServiceConsumer serviceConsumer;
}
