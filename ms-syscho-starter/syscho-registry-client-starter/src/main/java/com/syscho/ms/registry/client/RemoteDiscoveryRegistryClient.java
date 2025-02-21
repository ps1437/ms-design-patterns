package com.syscho.ms.registry.client;

import com.syscho.ms.registry.client.model.ServiceInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class RemoteDiscoveryRegistryClient implements DiscoveryRegistryClient {

    private final RestTemplate restTemplate;
    private final String registryServerUrl;

    public RemoteDiscoveryRegistryClient(RestTemplate restTemplate,
                                         @Value("${registry.server.url}") String registryServerUrl) {
        this.restTemplate = restTemplate;
        this.registryServerUrl = registryServerUrl;
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceName) {
        String url = registryServerUrl + "/registry/discover/" + serviceName;
        ServiceInstance[] instances = restTemplate.getForObject(url, ServiceInstance[].class);
        return instances != null ? Arrays.asList(instances) : List.of();
    }
}
