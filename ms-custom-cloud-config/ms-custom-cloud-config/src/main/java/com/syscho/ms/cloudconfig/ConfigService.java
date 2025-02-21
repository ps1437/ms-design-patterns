package com.syscho.ms.cloudconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class ConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigService.class);
    private static final String CONFIG_PATH = "C:\\praveeen\\preperation\\ms-custom-cloud-config\\ms-custom-cloud-config\\src\\main\\resources\\config-repo"; // Set absolute path to config directory
    private static final String DEFAULT_CONFIG_YML = "application.yml";
    private static final String DEFAULT_CONFIG_PROPERTIES = "application.properties";

    public Map<String, Object> getConfig(String appName) throws IOException {
        String configFileName = appName.equalsIgnoreCase("default") ? DEFAULT_CONFIG_YML : appName + ".yml";
        File configFile = new File(CONFIG_PATH + configFileName);

        if (!configFile.exists() && appName.equalsIgnoreCase("default")) {
            configFile = new File(CONFIG_PATH + DEFAULT_CONFIG_PROPERTIES);
        }

        if (!configFile.exists()) {
            LOGGER.error("Configuration file not found for {}", appName);
            throw new RuntimeException("Configuration file not found for " + appName);
        }

        LOGGER.info("Loading configuration from: {}", configFile.getAbsolutePath());
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(configFile, Map.class);
    }
}