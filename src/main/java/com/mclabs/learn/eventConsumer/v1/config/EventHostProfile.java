package com.mclabs.learn.eventConsumer.v1.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventHostProfile {
    private String hostname;
    private String vHost;
    private String username;
    private String password;
}
