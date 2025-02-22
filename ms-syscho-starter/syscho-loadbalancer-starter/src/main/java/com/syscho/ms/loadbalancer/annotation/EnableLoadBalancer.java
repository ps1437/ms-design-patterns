package com.syscho.ms.loadbalancer.annotation;

import com.syscho.ms.loadbalancer.LoadBalancerConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Import({LoadBalancerConfig.class})
@Configuration
@Qualifier("loadBalancedRestTemplate")
public @interface EnableLoadBalancer {
}
