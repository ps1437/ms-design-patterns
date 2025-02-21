package com.syscho.ms.loadbalancer.strategy;

import com.syscho.ms.loadbalancer.model.ServerInstance;

import java.util.List;

public class RoundRobinStrategy implements LoadBalancingStrategy {
    private int currentIndex = 0;

    @Override
    public ServerInstance selectServer(List<ServerInstance> serverInstances) {
        int attempts = 0;
        ServerInstance selectedServerInstance = null;

        while (attempts < serverInstances.size()) {
            selectedServerInstance = serverInstances.get(currentIndex);
            if (selectedServerInstance.isUp()) {
                break;
            }
            currentIndex = (currentIndex + 1) % serverInstances.size();
            attempts++;
        }

        currentIndex = (currentIndex + 1) % serverInstances.size();
        return selectedServerInstance;
    }
}
