package com.syscho.ms.loadbalancer.strategy;

import com.syscho.ms.loadbalancer.model.ServerInstance;

import java.util.List;

public class LeastConnectionsStrategy implements LoadBalancingStrategy {

    @Override
    public ServerInstance selectServer(List<ServerInstance> serverInstances) {
        ServerInstance selectedServerInstance = null;
        int minConnections = Integer.MAX_VALUE;

        for (ServerInstance serverInstance : serverInstances) {
            if (serverInstance.isUp() && serverInstance.activeConnections() < minConnections) {
                selectedServerInstance = serverInstance;
                minConnections = serverInstance.activeConnections();
            }
        }
        return selectedServerInstance;
    }
}
