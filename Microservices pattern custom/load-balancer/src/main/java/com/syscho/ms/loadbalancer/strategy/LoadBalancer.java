package com.syscho.ms.loadbalancer.strategy;

import com.syscho.ms.loadbalancer.model.Server;

import java.util.List;

public class LoadBalancer {
    private LoadBalancingStrategy strategy;

    public LoadBalancer(LoadBalancingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(LoadBalancingStrategy strategy) {
        this.strategy = strategy;
    }

    public Server selectServer(List<Server> servers) {
        return strategy.selectServer(servers);
    }
}
