package com.syscho.ms.loadbalancer;

import com.syscho.ms.loadbalancer.interceptor.LoadBalancerInterceptor;
import com.syscho.ms.loadbalancer.strategy.LoadBalancingStrategy;
import com.syscho.ms.loadbalancer.strategy.RoundRobinStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class LoadBalancerConfig {

    @Bean
    public LoadBalancerInterceptor loadBalancerInterceptor(LoadBalancer loadBalancer) {
        return new LoadBalancerInterceptor(loadBalancer);
    }

    @Bean
    @Qualifier("loadBalancedRestTemplate")
    public RestTemplate loadBalancedRestTemplate(LoadBalancerInterceptor loadBalancerInterceptor) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(loadBalancerInterceptor));
        return restTemplate;
    }

    @Bean
    public LoadBalancer loadBalancer(LoadBalancingStrategy loadBalancingStrategy) {
        return new LoadBalancer(loadBalancingStrategy);
    }

    @Bean
    public LoadBalancingStrategy loadBalancingStrategy() {
        return new RoundRobinStrategy();
    }
}
