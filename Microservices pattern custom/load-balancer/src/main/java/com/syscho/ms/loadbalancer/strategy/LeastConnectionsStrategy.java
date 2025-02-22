package com.syscho.ms.loadbalancer.strategy;

import com.syscho.ms.loadbalancer.model.Server;

import java.util.List;

public class LeastConnectionsStrategy implements LoadBalancingStrategy {

    @Override
    public Server selectServer(List<Server> servers) {
        Server selectedServer = null;
        int minConnections = Integer.MAX_VALUE;

        for (Server server : servers) {
            if (server.isUp() && server.activeConnections() < minConnections) {
                selectedServer = server;
                minConnections = server.activeConnections();
            }
        }
        return selectedServer;
    }
}
