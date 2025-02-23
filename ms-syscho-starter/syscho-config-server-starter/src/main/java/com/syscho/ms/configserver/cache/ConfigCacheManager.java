package com.syscho.ms.configserver.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Component
public class ConfigCacheManager {

    private final Map<String, Map<String, Object>> cache = new ConcurrentHashMap<>();

    public Map<String, Object> getOrLoad(String key, Supplier<Map<String, Object>> loader) {
        return cache.computeIfAbsent(key, k -> loader.get());
    }

    public void put(String key, Map<String, Object> loader) {
        cache.put(key, loader);
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public void clearCache() {
        cache.clear();
    }

}
