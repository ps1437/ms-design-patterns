package com.syscho.ms.registry.client.config;

import com.syscho.ms.registry.client.DiscoveryRegistryClient;
import com.syscho.ms.registry.client.RemoteDiscoveryRegistryClient;
import com.syscho.ms.registry.client.heartbeats.HeartBeatScheduler;
import com.syscho.ms.registry.client.model.ServiceInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Configuration
@Import({ServiceRegistryConfiguration.class,
        RemoteDiscoveryRegistryClient.class})
@EnableScheduling
public class ClientRegistryConfig {

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

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public HeartBeatScheduler heartBeatScheduler(ServiceInstance serviceInstance, RestTemplate restTemplate) {
        return new HeartBeatScheduler(serviceInstance, restTemplate);
    }
}