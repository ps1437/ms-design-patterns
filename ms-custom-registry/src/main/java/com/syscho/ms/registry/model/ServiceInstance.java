package com.syscho.ms.registry.model;

import lombok.Data;
import lombok.Getter;

import java.time.Instant;

@Data
public class ServiceInstance {
    private String serviceName;
    private String instanceId;
    private String host;
    private int port;
    private boolean isHealthy;
    @Getter
    private Instant lastHeartbeat;

    public ServiceInstance(String serviceName, String instanceId, String host, int port) {
        this.serviceName = serviceName;
        this.instanceId = instanceId;
        this.host = host;
        this.port = port;
        this.isHealthy = true;
        this.lastHeartbeat = Instant.now();
    }

    public void updateHeartbeat() {
        this.lastHeartbeat = Instant.now();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isHealthy() {
        return isHealthy;
    }

    public void setHealthy(boolean healthy) {
        isHealthy = healthy;
    }

    public Instant getLastHeartbeat() {
        return lastHeartbeat;
    }

    public void setLastHeartbeat(Instant lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }
}
