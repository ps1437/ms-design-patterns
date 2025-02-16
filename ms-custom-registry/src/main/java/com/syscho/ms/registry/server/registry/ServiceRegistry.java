package com.syscho.ms.registry.server.registry;

import com.syscho.ms.registry.model.ServiceInstance;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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

    public void heartbeat(String instanceId) {
        registry.forEach((serviceName, instances) -> {
            instances.stream()
                    .filter(service -> service.getInstanceId().equals(instanceId))
                    .forEach(ServiceInstance::updateHeartbeat);
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
}
