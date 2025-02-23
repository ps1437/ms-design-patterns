package com.syscho.config.loader;

import com.syscho.ms.cloudconfig.cache.ConfigCacheManager;
import com.syscho.ms.cloudconfig.config.ConfigProperties;
import com.syscho.ms.cloudconfig.loader.watcher.FileWatcherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@ConditionalOnProperty(name = "config.server.provider", havingValue = "FILESYSTEM")
@Service
@Slf4j
public class FileSystemLoaderYML extends FileLoader {

    private final ConfigProperties configProperties;

    public FileSystemLoaderYML(ConfigProperties configProperties, ConfigCacheManager cacheManager, FileWatcherService fileWatcherService) {
        super(cacheManager);
        this.configProperties = configProperties;
        fileWatcherService.watch(getDirectoryPath(), cacheManager);
    }

    @Override
    protected Path getDirectoryPath() {
        return Paths.get(configProperties.getPath());
    }

    @Override
    public Map<String, Object> loadFile(String fileName, String profile) {
        return loadPropertySources(fileName, profile);
    }

    @Override
    public Map<String, Object> loadFile(String filename) {
        return loadFile(filename, DEFAULT_PROFILE);
    }

}
