package com.syscho.ms.registry.server.controller;

import com.syscho.ms.registry.model.ServiceInstance;
import com.syscho.ms.registry.server.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registry/heartbeat")
public class HeartbeatController {

    private static final Logger logger = LoggerFactory.getLogger(HeartbeatController.class);
    private final ServiceRegistry serviceRegistry;

    public HeartbeatController(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @PostMapping
    public String heartbeat(@RequestBody ServiceInstance serviceInstance) {
        logger.info("Received heartbeat request from instance: {}", serviceInstance);
        serviceRegistry.heartbeat(serviceInstance);
        logger.info("Heartbeat processed for instance: {}", serviceInstance);
        return "Heartbeat received";
    }
}
