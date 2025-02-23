package com.syscho.ms.configserver.loader;

import com.syscho.ms.configserver.cache.ConfigCacheManager;
import com.syscho.ms.configserver.config.ConfigProperties;
import com.syscho.ms.configserver.loader.watcher.WatcherService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;
import java.util.Objects;

@ConditionalOnProperty(name = "config.server.provider", havingValue = "GITHUB")
@Slf4j
@Service
public class GitHubLoader extends FileLoader {

    private final ConfigProperties configProperties;

    public GitHubLoader(ConfigCacheManager cacheManager, ConfigProperties configProperties, WatcherService githubWatcher) {
        super(cacheManager, configProperties);
        this.configProperties = configProperties;
        githubWatcher.watch(configProperties.getDirectoryPath(), cacheManager);
    }

    @Override
    public Map<String, Object> loadFile(String fileName, String profile) {
        cloneOrUpdateRepo();
        return loadPropertySources(fileName, profile);
    }

    @Override
    public Map<String, Object> loadFile(String filename) {
        return loadFile(filename, DEFAULT_PROFILE);
    }


    @Override
    public void clearCache() {
        log.info("Refreshing configuration from Git...");
        super.clearCache();
        cloneOrUpdateRepo();
    }

    private void cloneOrUpdateRepo() {
        Objects.requireNonNull(configProperties, "configProperties should not be null");
        Objects.requireNonNull(configProperties.getPath(), "Git repository path is required");
        File targetDir = configProperties.getDirectoryPath().toFile();
        log.info("Git repository directory: {}", targetDir.getPath());

        try {
            if (!targetDir.exists()) {
                log.info("Cloning Git repository...");
                Git.cloneRepository()
                        .setURI(configProperties.getPath())
                        .setDirectory(targetDir)
                        .setBranch(configProperties.getDefaultLabel())
                        .call()
                        .close();
                log.info("Repository cloned successfully.");
            }
        } catch (GitAPIException e) {
            log.error("Error accessing Git repository: ", e);
        }
    }


}
