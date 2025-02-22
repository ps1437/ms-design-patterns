package com.syscho.ms.registry.server.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RegistryServiceCleanUpJob {

    private static final Logger logger = LoggerFactory.getLogger(RegistryServiceCleanUpJob.class);

    private final ServiceRegistry serviceRegistry;

    public RegistryServiceCleanUpJob(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Scheduled(fixedRate = 30000)
    public void removeStaleServices() {
        logger.info("Running schedule job for cleanup at {}", Instant.now().toString());
        serviceRegistry.removeStaleServices();
    }
}
