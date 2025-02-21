package com.syscho.ms.registry.client.model;

import lombok.Data;

import java.time.Instant;

@Data
public class ServiceInstance {
    private String serviceName;
    private String instanceId;
    private String host;
    private int port;
    private boolean isHealthy;
    private Instant lastHeartbeat;

    public ServiceInstance(String serviceName, String instanceId, String host, int port) {
        this.serviceName = serviceName;
        this.instanceId = instanceId;
        this.host = host;
        this.port = port;
        this.isHealthy = true;
        this.lastHeartbeat = Instant.now();
    }

    public String getUri() {
        return host + ":" + port;
    }
}
