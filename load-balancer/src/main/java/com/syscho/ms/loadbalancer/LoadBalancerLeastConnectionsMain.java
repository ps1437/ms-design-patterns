package com.syscho.ms.loadbalancer;

import com.syscho.ms.loadbalancer.model.Server;
import com.syscho.ms.loadbalancer.strategy.LeastConnectionsStrategy;
import com.syscho.ms.loadbalancer.strategy.LoadBalancer;
import com.syscho.ms.loadbalancer.strategy.LoadBalancingStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadBalancerLeastConnectionsMain {

    public static void main(String[] args) {

        List<Server> serverPool = Arrays.asList(
                new Server("Server1", true, 0),
                new Server("Server2", true, 0),
                new Server("Server3", true, 0)
        );

        LoadBalancingStrategy strategy = new LeastConnectionsStrategy();
        LoadBalancer loadBalancer = new LoadBalancer(strategy);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        //  10 concurrent simulation concurrency
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                Server server = loadBalancer.selectServer(serverPool);
                server.increaseActiveConnections();
                System.out.println("Request handled by: " + server.getId());
                System.out.println("Active connections for " + server.getId() + ": " + server.activeConnections());
                System.out.println("----------------------");
            });
        }

        executorService.shutdown();
    }
}
