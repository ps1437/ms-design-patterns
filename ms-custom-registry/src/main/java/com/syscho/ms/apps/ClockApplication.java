package com.syscho.ms.apps;

import com.syscho.ms.registry.client.HeartBeatScheduler;
import com.syscho.ms.registry.client.ServiceRegistryConfiguration;
import com.syscho.ms.registry.client.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
@Import({HeartBeatScheduler.class, ServiceRegistryConfiguration.class , Config.class})
public class ClockApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ClockApplication.class);
        Map<String, Object> props = new HashMap<>();
        props.put("spring.application.name", "Hello-service");
        app.setDefaultProperties(props);
        app.run(args);

    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
