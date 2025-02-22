package com.syscho.ms.loadbalancer.strategy;

import com.syscho.ms.loadbalancer.model.Server;

import java.util.List;

public interface LoadBalancingStrategy {
    Server selectServer(List<Server> servers);
}
