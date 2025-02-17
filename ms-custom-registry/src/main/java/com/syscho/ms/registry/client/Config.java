package com.syscho.ms.registry.client;

import com.syscho.ms.registry.model.ServiceInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class Config {

    @Value("${spring.application.name:UNKNOWN}")
    private String serviceName;

    @Value("${server.port:8080}")
    private int port;

    @Value("${client.ip-address:localhost}")
    private String ipAddress;

    @Bean("client-instance-id")
    public String instanceId() {
        return UUID.randomUUID().toString();
    }

    @Bean
    public ServiceInstance serviceInstance() {
        return new ServiceInstance(serviceName, instanceId(), ipAddress, port);
    }

}
