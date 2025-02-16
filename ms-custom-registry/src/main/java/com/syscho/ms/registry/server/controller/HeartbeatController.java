package com.syscho.ms.registry.server.controller;

import com.syscho.ms.registry.server.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{instanceId}")
    public String heartbeat(@PathVariable String instanceId) {
        logger.info("Received heartbeat request from instance: {}", instanceId);
        serviceRegistry.heartbeat(instanceId);
        logger.info("Heartbeat processed for instance: {}", instanceId);
        return "Heartbeat received";
    }
}
