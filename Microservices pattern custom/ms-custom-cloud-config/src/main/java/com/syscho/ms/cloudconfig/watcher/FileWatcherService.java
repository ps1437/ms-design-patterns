package com.syscho.ms.cloudconfig.watcher;

import com.syscho.ms.cloudconfig.cache.ConfigCacheManager;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class FileWatcherService implements WatcherService {

    private final ExecutorService watcherServiceExecutor = Executors.newSingleThreadExecutor();

    @Override
    public void watch(Path directory, ConfigCacheManager cacheManager) {
        log.info("File watcher service is started !!");

        watcherServiceExecutor.submit(() -> {
            try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                directory.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (true) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                            Path changedFile = directory.resolve((Path) event.context());
                            log.warn("Configuration file changed: " + changedFile);

                            //removed the file from cache so that it will be reloaded from file system
                            String cachedKey = changedFile.getFileName().toString().split("\\.")[0];
                            cacheManager.remove(cachedKey);
                        }
                    }
                    key.reset();
                }
            } catch (IOException | InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Error watching config files", e);
            } finally {
                this.destroy();
            }
        });
    }

    @PreDestroy
    void destroy() {
        watcherServiceExecutor.shutdown();
        log.info("File Watcher service is stopped !!");
    }
}
