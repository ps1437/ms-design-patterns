package com.syscho.config.loader;

import com.syscho.ms.cloudconfig.cache.ConfigCacheManager;
import com.syscho.ms.cloudconfig.config.ConfigProperties;
import com.syscho.ms.cloudconfig.loader.watcher.GithubWatcher;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

@ConditionalOnProperty(name = "config.server.provider", havingValue = "GITHUB")
@Slf4j
@Service
public class GitHubLoader extends FileLoader {

    private final ConfigProperties configProperties;
    private final ConfigCacheManager cacheManager;
    private final Path repoDirectory;

    public GitHubLoader(ConfigCacheManager cacheManager, ConfigProperties configProperties ,GithubWatcher githubWatcher) {
        super(cacheManager);
        this.cacheManager = cacheManager;
        this.configProperties = configProperties;
        this.repoDirectory = getClonedPath();
        githubWatcher.watch(repoDirectory,cacheManager);
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
    protected Path getDirectoryPath() {
        return repoDirectory.resolve(configProperties.getFolderPath());
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

        File targetDir = repoDirectory.toFile();
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
            } else {
//                log.info("Pulling latest changes from Git...");
//                try (Git git = Git.open(targetDir)) {
//                    git.pull().call();
//                }
            }
        } catch (GitAPIException e) {
            log.error("Error accessing Git repository: ", e);
        }
    }

    private Path getClonedPath() {
        String repoName = extractRepoName(configProperties.getPath());
        if (repoName == null) {
            throw new IllegalArgumentException("Invalid Git repository URL format: " + configProperties.getPath());
        }
        return Paths.get(System.getProperty("user.home"), repoName);
    }

    private static String extractRepoName(String gitUrl) {
        if (gitUrl == null || !gitUrl.endsWith(".git")) {
            return null;
        }
        return gitUrl.substring(gitUrl.lastIndexOf('/') + 1, gitUrl.length() - 4);
    }

}
