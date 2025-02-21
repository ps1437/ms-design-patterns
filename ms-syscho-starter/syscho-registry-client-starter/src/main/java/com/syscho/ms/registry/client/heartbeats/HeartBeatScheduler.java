package com.syscho.ms.registry.client.heartbeats;

import com.syscho.ms.registry.client.model.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class HeartBeatScheduler {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatScheduler.class);
    private final RestTemplate restTemplate;
    private final ServiceInstance serviceInstance;

    @Value("${service-registry.url}")
    private String registryBaseUrl;

    public HeartBeatScheduler(ServiceInstance serviceInstance, RestTemplate restTemplate) {
        this.serviceInstance = serviceInstance;
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 30000)
    public void updateHealth() {
        logger.info(".......updating heartbeats........");
        String url = registryBaseUrl + "/heartbeat";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ServiceInstance> requestEntity = new HttpEntity<>(serviceInstance, headers);

        restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        try {
            restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            logger.info("Heartbeat successfully sent.");
        } catch (HttpClientErrorException ex) {
            logger.error("Failed to send heartbeat: {}", ex.getMessage());
        }

    }

}
