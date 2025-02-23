package com.syscho.ms.configserver.loader.watcher;


import com.syscho.ms.configserver.cache.ConfigCacheManager;

import java.nio.file.Path;

public interface WatcherService {
    void watch(Path directory, ConfigCacheManager cacheManager);
}