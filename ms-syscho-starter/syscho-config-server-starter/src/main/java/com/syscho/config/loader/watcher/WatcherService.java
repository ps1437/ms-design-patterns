package com.syscho.config.loader.watcher;

import com.syscho.ms.cloudconfig.cache.ConfigCacheManager;

import java.nio.file.Path;

public interface WatcherService {
    void watch(Path directory, ConfigCacheManager cacheManager);
}