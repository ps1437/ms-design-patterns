package com.syscho.ms.registry.client;

import com.syscho.ms.registry.model.ServiceInstance;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class ServiceRegistrarConfigurer implements ApplicationListener<ContextRefreshedEvent>, DisposableBean {

    private final RestTemplate restTemplate;
    private final String instanceId;

    @Value("${service-registry.url}")
    private String registryBaseUrl;

    @Value("${spring.application.name:UNKNOWN}")
    private String serviceName;

    @Value("${server.port:8080}")
    private int port;

    @Value("${client.ip-address:localhost}")
    private String ipAddress;


    public ServiceRegistrarConfigurer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.instanceId = UUID.randomUUID().toString();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String registryUrl = registryBaseUrl + "/register";
        ServiceInstance service = new ServiceInstance(serviceName, instanceId, ipAddress, port);
        restTemplate.postForEntity(registryUrl, service, String.class);
    }

    @Override
    public void destroy() {
        String deregisterUrl = registryBaseUrl + "/deregister/" + instanceId;
        restTemplate.delete(deregisterUrl);
    }


    @Bean("client-instance-id")
    public String instanceId() {
        return instanceId;
    }

}
