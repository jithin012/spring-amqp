package com.mclabs.learn.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Getter
@Setter
@ToString
public class RabbitmqConfigProperties {
    private String host;
    private String username;
    private String password;
    private int port;
    private String virtualHost;
    private String uri;
}
