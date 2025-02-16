package com.syscho.ms.registry.server.controller;

import com.syscho.ms.registry.model.ServiceInstance;
import com.syscho.ms.registry.server.registry.ServiceRegistry;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/registry")
public class RegistryController {

    private final ServiceRegistry serviceRegistry;

    public RegistryController(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @PostMapping("/register")
    public String register(@RequestBody ServiceInstance service) {
        serviceRegistry.register(service);
        return "Service Registered Successfully";
    }

    @GetMapping("/discover/{serviceName}")
    public List<ServiceInstance> discover(@PathVariable String serviceName) {
        return serviceRegistry.discover(serviceName);
    }

    @GetMapping("/discovers")
    public Collection<List<ServiceInstance>> discover() {
        return serviceRegistry.getAllServices();
    }
}
