package com.syscho.ms.loadbalancer.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private final String id;
    private boolean isUp;
    private AtomicInteger activeConnections;

    public Server(String id, boolean isUp, int activeConnections) {
        if (id == null) {
            throw new IllegalArgumentException("Server ID cannot be null");
        }
        this.id = id;
        this.isUp = isUp;
        this.activeConnections = new AtomicInteger(activeConnections);
    }

    public String getId() {
        return id;
    }

    public boolean isUp() {
        return isUp;
    }

    public int activeConnections() {
        return activeConnections.get();
    }

    public void setIsUp(boolean isUp) {
        this.isUp = isUp;
    }

    public void increaseActiveConnections() {
        activeConnections.incrementAndGet();
    }

    public void decreaseActiveConnections() {
        activeConnections.decrementAndGet();
    }
}
