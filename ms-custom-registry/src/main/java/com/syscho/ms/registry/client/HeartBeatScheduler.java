package com.syscho.ms.registry.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HeartBeatScheduler {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatScheduler.class);
    private final RestTemplate restTemplate;
    private final String instanceId;

    @Value("${service-registry.url}")
    private String registryBaseUrl;

    public HeartBeatScheduler(RestTemplate restTemplate, @Qualifier("client-instance-id")String instanceId) {
        this.restTemplate = restTemplate;
        this.instanceId = instanceId;
    }

    @Scheduled(fixedRate = 30000)
    public void updateHealth() {
        logger.info(".......updating heartbeats........");
        String url = registryBaseUrl + "/heartbeat/" + instanceId;
        restTemplate.getForEntity(url, String.class);
    }
}
