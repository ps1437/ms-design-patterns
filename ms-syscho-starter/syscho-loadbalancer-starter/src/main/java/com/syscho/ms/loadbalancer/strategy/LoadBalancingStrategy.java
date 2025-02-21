package com.syscho.ms.loadbalancer.strategy;

import com.syscho.ms.loadbalancer.model.ServerInstance;

import java.util.List;

public interface LoadBalancingStrategy {
    ServerInstance selectServer(List<ServerInstance> serverInstances);
}
