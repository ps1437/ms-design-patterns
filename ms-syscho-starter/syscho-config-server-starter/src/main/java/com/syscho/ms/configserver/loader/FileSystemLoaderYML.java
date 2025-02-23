package com.syscho.ms.configserver.loader;

import com.syscho.ms.configserver.cache.ConfigCacheManager;
import com.syscho.ms.configserver.config.ConfigProperties;
import com.syscho.ms.configserver.loader.watcher.WatcherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;

@ConditionalOnProperty(name = "config.server.provider", havingValue = "FILESYSTEM")
@Service
@Slf4j
public class FileSystemLoaderYML extends FileLoader {

    public FileSystemLoaderYML(ConfigProperties configProperties, ConfigCacheManager cacheManager, WatcherService watcherService) {
        super(cacheManager, configProperties);
        watcherService.watch(configProperties.getDirectoryPath(), cacheManager);
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
