package com.syscho.ms.loadbalancer;

import com.syscho.ms.loadbalancer.model.Server;
import com.syscho.ms.loadbalancer.strategy.LoadBalancer;
import com.syscho.ms.loadbalancer.strategy.LoadBalancingStrategy;
import com.syscho.ms.loadbalancer.strategy.RoundRobinStrategy;

import java.util.Arrays;
import java.util.List;

public class LoadBalancerRoundRobinMain {

    public static void main(String[] args) {
        List<Server> serverPool = Arrays.asList(
                new Server("Server1", true, 0),
                new Server("Server2", true, 0),
                new Server("Server3", true, 0)
        );

        LoadBalancingStrategy roundRobinStrategy = new RoundRobinStrategy();
        LoadBalancer loadBalancer = new LoadBalancer(roundRobinStrategy);

        System.out.println("Using Round Robin Strategy:");
        for (int i = 0; i < 7; i++) {
            Server server = loadBalancer.selectServer(serverPool);
            server.increaseActiveConnections();
            System.out.println("Request handled by: " + server.getId());
            System.out.println("----------------------");
            server.decreaseActiveConnections();
        }

    }
}
