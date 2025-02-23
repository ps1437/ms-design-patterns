package com.syscho.config.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.syscho.ms.cloudconfig.cache.ConfigCacheManager;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class FileLoader {

    protected final String DEFAULT_APPLICATION_YML = "application.yml";
    protected final String EXTENSION_YML = ".yml";
    protected final String DEFAULT_PROFILE = "default";
    private final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());
    private final ConfigCacheManager cacheManager;

    public FileLoader(ConfigCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    protected Map<String, Object> loadAndCreatePropertySource(Path filePath) throws IOException {
        if (!filePath.toFile().exists()) {
            return Collections.emptyMap();
        }
        Map<String, Object> source = readYamlFile(filePath);
        return Map.of("name", filePath.toString(), "source", source);
    }

    protected abstract Path getDirectoryPath();

    private Map<String, Object> readYamlFile(Path filePath) throws IOException {
        File file = filePath.toFile();
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
        return YAML_MAPPER.readValue(file, new TypeReference<>() {
        });
    }

    Map<String, Object> createPropertySourcesMap(String fileName, String profile, List<Map<String, Object>> propertySources) {
        return Map.of("name", fileName, "profiles", Collections.singletonList(profile), "propertySources", propertySources);
    }

    String getCacheKey(Path filePath) {
        return filePath.getFileName().toString().split("\\.")[0];
    }


    protected Map<String, Object> loadPropertySources(String fileName, String profile) {
        List<Map<String, Object>> propertySources = new LinkedList<>();

        Path directoryPath = getDirectoryPath();
        addIfExists(propertySources, directoryPath.resolve(DEFAULT_APPLICATION_YML)); // application.yml

        if (!"application".equalsIgnoreCase(fileName)) {
            addIfExists(propertySources, directoryPath.resolve(fileName + ".yml")); // {service-name}.yml
        }

        addIfExists(propertySources, directoryPath.resolve(fileName + "-" + profile + EXTENSION_YML)); //{service-name-{profile}}.yml

        return createPropertySourcesMap(fileName, profile, propertySources);
    }

    private void addIfExists(List<Map<String, Object>> propertySources, Path filePath) {
        if (filePath.toFile().exists()) {
            String cacheKey = getCacheKey(filePath);
            Map<String, Object> propertySource = cacheManager.getOrLoad(cacheKey, () -> {
                try {
                    log.info("....Loading {} from file System.... !!", cacheKey);
                    return loadAndCreatePropertySource(filePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            propertySources.add(propertySource);
        }
    }

    public void clearCache() {
        cacheManager.clearCache();
    }

    public abstract Map<String, Object> loadFile(String filename, String profile) throws IOException;

    public abstract Map<String, Object> loadFile(String filename) throws IOException;


}
