package com.syscho.ms.loadbalancer.strategy;

import com.syscho.ms.loadbalancer.model.Server;

import java.util.List;

public class RoundRobinStrategy implements LoadBalancingStrategy {
    private int currentIndex = 0;

    @Override
    public Server selectServer(List<Server> servers) {
        int attempts = 0;
        Server selectedServer = null;

        while (attempts < servers.size()) {
            selectedServer = servers.get(currentIndex);
            if (selectedServer.isUp()) {
                break;
            }
            currentIndex = (currentIndex + 1) % servers.size();
            attempts++;
        }

        currentIndex = (currentIndex + 1) % servers.size();
        return selectedServer;
    }
}
