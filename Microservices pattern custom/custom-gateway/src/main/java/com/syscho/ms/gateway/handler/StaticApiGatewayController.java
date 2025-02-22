package com.syscho.ms.gateway.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
public class StaticApiGatewayController {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    @Autowired
    public StaticApiGatewayController(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/api")
    public ResponseEntity<String> handleRequest(@RequestParam String name,
                                                @RequestParam String apiPath) {
        Optional<String> serviceUrls = discoveryClient.getInstances(name).stream()
                .map(instance -> instance.getUri().toString())
                .findFirst();

        if (serviceUrls.isPresent()) {
            String targetUrl = serviceUrls.get();
            String finalUrl = targetUrl + "/" + apiPath;
            String response = restTemplate.getForObject(finalUrl, String.class);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body("Service not found");
        }
    }
}
