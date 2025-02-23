package com.syscho.ms.configclient;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//@SpringBootApplication
@Component
@RequiredArgsConstructor
public class ConfigClient implements CommandLineRunner {

    private final ConfigurableEnvironment environment;
    private final RestTemplate restTemplate = new RestTemplate();
    private final Environment springEnv;

    public static void main(String[] args) {
        SpringApplication.run(ConfigClient.class, args);
    }

    @Override
    public void run(String... args) {
        loadConfiguration();
    }

    public void loadConfiguration() {
        final String url = getUri();

        Map<String, Object> configData = restTemplate.getForObject(url, Map.class);

        if (configData == null) {
            throw new RuntimeException("Failed to fetch configuration from server");
        }

        List<Map<String, Object>> propertySources = (List<Map<String, Object>>) configData.get("propertySources");

        MutablePropertySources propertySourcesContainer = environment.getPropertySources();

        for (Map<String, Object> propertySource : propertySources) {
            String name = (String) propertySource.get("name");
            Map<String, Object> source = (Map<String, Object>) propertySource.get("source");

            Map<String, Object> flattenedProps = flattenProperties(source, "");

            PropertySource<?> mapPropertySource = new MapPropertySource(name, flattenedProps);
            propertySourcesContainer.addFirst(mapPropertySource);
        }
    }

    private String getUri() {
        String fileName = springEnv.getProperty("spring.application.name", "application");
        String profile = springEnv.getProperty("spring.profiles.active", "default");
        String configServerUrl = springEnv.getProperty("config.server-url", "localhost:8888");
        return configServerUrl + "/" + fileName + "/" + profile;
    }

    private Map<String, Object> flattenProperties(Map<String, Object> source, String prefix) {
        Map<String, Object> flattened = new LinkedHashMap<>();
        source.forEach((key, value) -> {
            String newKey = prefix.isEmpty() ? key : prefix + "." + key;
            if (value instanceof Map) {
                flattened.putAll(flattenProperties((Map<String, Object>) value, newKey));
            } else {
                flattened.put(newKey, value);
            }
        });
        return flattened;
    }

}
