package com.syscho.ms.registry.client.annotation;

import com.syscho.ms.registry.client.config.ClientRegistryConfig;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ClientRegistryConfig.class)
public @interface EnableClientServiceRegistry {
}
