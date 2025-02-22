package com.syscho.ms.cloudconfig.loader;

import com.syscho.ms.cloudconfig.config.ConfigProperties;
import com.syscho.ms.cloudconfig.cache.ConfigCacheManager;
import com.syscho.ms.cloudconfig.watcher.FileWatcherService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class FileSystemLoaderYML implements FileLoader {

    private final Path directoryPath;
    private final ConfigCacheManager cacheManager;

    public FileSystemLoaderYML(ConfigProperties configProperties, ConfigCacheManager cacheManager, FileWatcherService fileWatcherService) {
        this.directoryPath = Paths.get(Objects.requireNonNull(configProperties.getPath(), "Path is required"));
        this.cacheManager = cacheManager;
      //  fileWatcherService.watch(directoryPath, cacheManager);
    }

    @Override
    public Map<String, Object> loadFile(String fileName, String profile) {
        String cacheKey = FileLoader.getCacheKey(fileName, profile);
        return cacheManager.getOrLoad(cacheKey, () -> loadPropertySources(fileName, profile));
    }

    @Override
    public Map<String, Object> loadFile(String filename) {
        return loadFile(filename, DEFAULT_PROFILE);
    }

    @Override
    public void refresh() {
        cacheManager.clearCache();
    }


    private Map<String, Object> loadPropertySources(String fileName, String profile) {
        log.info("Loading from file System !!");
        List<Map<String, Object>> propertySources = new LinkedList<>();

        addIfExists(propertySources, directoryPath.resolve(DEFAULT_APPLICATION_YML)); // application.yml

        if (!"application".equalsIgnoreCase(fileName)) {
            addIfExists(propertySources, directoryPath.resolve(fileName + ".yml")); // {service-name}.yml
        }

        addIfExists(propertySources, directoryPath.resolve(fileName + "-" + profile + EXTENSION_YML)); //{service-name-{profile}}.yml

        return FileLoader.createPropertySourcesMap(fileName, profile, propertySources);
    }

    private void addIfExists(List<Map<String, Object>> propertySources, Path filePath) {
        if (filePath.toFile().exists()) {
            propertySources.add(FileLoader.createPropertySource(filePath));
        }
    }


}
