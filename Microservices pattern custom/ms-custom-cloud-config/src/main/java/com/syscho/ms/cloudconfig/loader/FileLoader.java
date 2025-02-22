package com.syscho.ms.cloudconfig.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.awt.geom.IllegalPathStateException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface FileLoader {

    String DEFAULT_APPLICATION_YML = "application.yml";
    String EXTENSION_YML = ".yml";
    String DEFAULT_PROFILE = "default";
    ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    Map<String, Object> loadFile(String filename, String profile) throws IOException;

    Map<String, Object> loadFile(String filename) throws IOException;

    void refresh();


    static Map<String, Object> createPropertySource(Path filePath) {
        if (!filePath.toFile().exists()) {
            return Collections.emptyMap();
        }
        Map<String, Object> source;
        try {
            source = readYamlFile(filePath);
        } catch (IOException e) {
            throw new IllegalPathStateException(filePath + " Invalid path");
        }
        return Map.of(
                "name", filePath.toString(),
                "source", source
        );
    }

    static Map<String, Object> readYamlFile(Path filePath) throws IOException {
        File file = filePath.toFile();
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
        return YAML_MAPPER.readValue(file, new TypeReference<>() {
        });
    }

    static Map<String, Object> createPropertySourcesMap(String fileName, String profile, List<Map<String, Object>> propertySources) {
        return Map.of(
                "name", fileName,
                "profiles", Collections.singletonList(profile),
                "propertySources", propertySources
        );
    }

    static String getCacheKey(String fileName, String profile) {
        if (DEFAULT_PROFILE.equalsIgnoreCase(profile)) {
            return fileName;
        }
        return fileName + "-" + profile;
    }
}
