package com.syscho.ms.loadbalancer;

import com.syscho.ms.loadbalancer.model.ServerInstance;
import com.syscho.ms.loadbalancer.strategy.LoadBalancingStrategy;

import java.util.List;


public class LoadBalancer {

    private LoadBalancingStrategy strategy;

    public LoadBalancer(LoadBalancingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(LoadBalancingStrategy strategy) {
        this.strategy = strategy;
    }

    public ServerInstance selectServer(List<ServerInstance> serverInstances) {
        ServerInstance selectedServerInstance = strategy.selectServer(serverInstances);
        if (selectedServerInstance == null) {
            throw new RuntimeException("No available servers");
        }
        return selectedServerInstance;
    }
}
