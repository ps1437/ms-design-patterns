package com.syscho.ms.gateway.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Enumeration;
import java.util.Optional;

@RestController
@RequestMapping("/gateway")
public class DynamicApiGatewayController {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    public DynamicApiGatewayController(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/{serviceId}/**")
    public ResponseEntity<String> handleRequest(HttpServletRequest request,
                                                @PathVariable String serviceId,
                                                @RequestBody(required = false) String body) {

        String targetUrl = getInstances(serviceId);
        String apiPath = request.getRequestURI().substring(("/gateway/" + serviceId).length());

        String finalUrl = targetUrl + apiPath;

        HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
        HttpHeaders headers = copyHttpHeaders(request);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(finalUrl, httpMethod, entity, String.class);

        return ResponseEntity.status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody());
    }


    private HttpHeaders copyHttpHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.add(headerName, request.getHeader(headerName));
        }
        return headers;
    }


    private String getInstances(String serviceId) {
        Optional<String> serviceUrls = discoveryClient.getInstances(serviceId).stream()
                .map(instance -> instance.getUri().toString())
                .findFirst();

        if (serviceUrls.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not found: " + serviceId);
        }

        return serviceUrls.get();
    }
}
