package com.syscho.ms.gateway;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.netflix.eureka.EurekaServiceInstance;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
public class RegionRoutingFilter implements GlobalFilter, Ordered {

    private final DiscoveryClient discoveryClient;

    public RegionRoutingFilter(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String region = serverHttpRequest.getHeaders().getFirst("X-Region");
        DefaultResponse attribute = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_LOADBALANCER_RESPONSE_ATTR);
        EurekaServiceInstance serviceInstance = (EurekaServiceInstance) attribute.getServer();
        if (region == null || region.isBlank()) {
            return chain.filter(exchange);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances(serviceInstance.getInstanceInfo().getAppName());

        Optional<ServiceInstance> matchedInstance = instances.stream()
                .filter(instance -> region.equalsIgnoreCase(instance.getMetadata().get("zone")))
                .findFirst();

        if (matchedInstance.isPresent()) {
            ServiceInstance instance = matchedInstance.get();
            URI targetUri = instance.getUri();

            String finalUrl = targetUri.toString() + serverHttpRequest.getURI().getRawPath();
//            if (serverHttpRequest.getURI().getQuery() != null) {
//                finalUrl += "?" + serverHttpRequest.getURI().getQuery();
//            }

            ServerHttpRequest modifiedRequest = serverHttpRequest.mutate()
                    .uri(URI.create(finalUrl))
                    .build();
            exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, URI.create(finalUrl));
            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        }
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
