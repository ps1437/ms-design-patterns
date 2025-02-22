package com.syscho.ms.registry.server.registry;

import com.syscho.ms.registry.model.ServiceInstance;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ServiceRegistry {

    private static final Logger logger = LoggerFactory.getLogger(ServiceRegistry.class);

    private final ConcurrentHashMap<String, List<ServiceInstance>> registry = new ConcurrentHashMap<>();

    public void register(ServiceInstance service) {
        registry.compute(service.getServiceName(), (key, existingInstances) -> {
            if (existingInstances == null) {
                return new ArrayList<>(Collections.singletonList(service));
            }
            existingInstances.add(service);
            return existingInstances;
        });
    }

    public void heartbeat(ServiceInstance requestServiceInstance) {

        if (registry.isEmpty()) {
            register(requestServiceInstance);
            return;
        }

        registry.forEach((serviceName, instances) -> {
            Optional<ServiceInstance> instanceOptional = instances.stream()
                    .filter(service -> service.getInstanceId().equals(requestServiceInstance.getInstanceId()))
                    .findFirst();

            if (instanceOptional.isPresent()) {
                instanceOptional.get().updateHeartbeat();
            } else {
                register(requestServiceInstance);
            }
        });
    }

    public List<ServiceInstance> discover(String serviceName) {
        return registry.getOrDefault(serviceName, Collections.emptyList());
    }

    public void removeStaleServices() {
        final Instant now = Instant.now();
        registry.forEach((serviceName, instances) -> {
            boolean isRemoved = instances.removeIf(service -> Duration.between(service.getLastHeartbeat(), now).toSeconds() > 60);
            if (isRemoved) {
                logger.info("Removed service from registry : name : {}  instance  {}", serviceName, instances);
            }
        });
    }

    public Collection<List<ServiceInstance>> getAllServices() {
        return registry.values();
    }

    public String getServiceUrl(String serviceName) {
        List<ServiceInstance> instances = registry.get(serviceName);
        if (instances != null && !instances.isEmpty()) {
            ServiceInstance serviceInstance = instances.get(0);
            return serviceInstance.getHost() + ":" + serviceInstance.getPort();
        }
        return null;
    }

}
