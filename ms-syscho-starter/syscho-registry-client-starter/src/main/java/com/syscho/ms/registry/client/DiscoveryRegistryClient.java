package com.syscho.ms.registry.client;

import com.syscho.ms.registry.client.model.ServiceInstance;

import java.util.List;

public interface DiscoveryRegistryClient {

    List<ServiceInstance> getInstances(String serviceName);
}
