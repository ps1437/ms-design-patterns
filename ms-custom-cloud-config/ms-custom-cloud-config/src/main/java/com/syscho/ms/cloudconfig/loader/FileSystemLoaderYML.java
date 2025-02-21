package com.syscho.ms.cloudconfig.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class FileSystemLoaderYML implements FileLoader {

    public static final String EXTENSION_YML = ".yml";
    public static final String DEFAULT = "default";

    private final String directoryPath;
    private final ObjectMapper yamlMapper;

    public FileSystemLoaderYML(@Value("${file-loader.path}") String directoryPath) {
        this.directoryPath = directoryPath;
        yamlMapper = new ObjectMapper(new YAMLFactory());
    }

    public Map<String, Object> loadFile(String fileName, String profile) throws IOException {
        String filePath = directoryPath + "\\" + fileName;
        if (profile.equalsIgnoreCase(DEFAULT)) {
            filePath += EXTENSION_YML;
        } else {
            filePath += "-" + profile + EXTENSION_YML;
        }
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("File not found: " + fileName);
        }
        return yamlMapper.readValue(file, Map.class);
    }
}
