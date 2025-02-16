package com.syscho.ms.serviceregistry;

import java.util.HashMap;
import java.util.Map;

public class ServiceRegistry {
    private static final Map<String, String> services = new HashMap<>();

    public static void register(String serviceName, String url) {
        services.put(serviceName, url);
    }

    public static String getServiceUrl(String serviceName) {
        return services.getOrDefault(serviceName, "Service Not Found");
    }

    public static void main(String[] args) {
        register("UserService", "http://localhost:8081/user");
        register("OrderService", "http://localhost:8082/order");

        System.out.println("UserService URL: " + getServiceUrl("UserService"));
        System.out.println("OrderService URL: " + getServiceUrl("OrderService"));
    }
}
