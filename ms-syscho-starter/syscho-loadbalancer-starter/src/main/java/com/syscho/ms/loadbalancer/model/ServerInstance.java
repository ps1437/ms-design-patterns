package com.syscho.ms.loadbalancer.model;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class ServerInstance {

    private final String uri;
    private boolean isUp;
    private AtomicInteger activeConnections;

    public ServerInstance(String uri, boolean isUp, int activeConnections) {
        if (uri == null) {
            throw new IllegalArgumentException("Server ID cannot be null");
        }
        this.uri = uri;
        this.isUp = isUp;
        this.activeConnections = new AtomicInteger(activeConnections);
    }

    public boolean isUp() {
        return isUp;
    }

    public int activeConnections() {
        return activeConnections.get();
    }

}
