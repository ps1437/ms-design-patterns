package com.syscho.ms.configserver.loader.watcher;

import com.syscho.ms.configserver.cache.ConfigCacheManager;
import com.syscho.ms.configserver.config.ConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@ConditionalOnProperty(name = "config.server.provider", havingValue = "GITHUB")
@Service
@Slf4j
public class GithubWatcher implements WatcherService {

    private final ConfigCacheManager cacheManager;
    private final ConfigProperties configProperties;
    private Path directory;

    public GithubWatcher(ConfigCacheManager cacheManager, ConfigProperties configProperties) {
        this.cacheManager = cacheManager;
        this.configProperties = configProperties;
    }

    @Override
    public void watch(Path directory, ConfigCacheManager cacheManager) {
        this.directory = directory;
    }

    @Scheduled(fixedDelay = 30000)
    public void checkForUpdates() {
        File repoDir = directory.toFile();
        if (!repoDir.exists()) {
            log.info("Local repository not found. Cloning...");
            cloneRepository(repoDir);
            return;
        }

        try (Repository repository = new FileRepositoryBuilder()
                .setGitDir(new File(directory.toFile(), ".git"))
                .readEnvironment()
                .findGitDir()
                .build();
             Git git = new Git(repository)) {

            if (repository.resolve("HEAD") == null) {
                log.warn("No commits found in the repository.");
                return;
            }

            String latestCommitBefore = repository.resolve("HEAD").getName();
            log.info("Current HEAD: {}", latestCommitBefore);

            git.pull().call();

            if (repository.resolve("HEAD") == null) {
                log.warn("Repository still has no commits after pull.");
                return;
            }

            String latestCommitAfter = repository.resolve("HEAD").getName();

            if (!latestCommitAfter.equals(latestCommitBefore)) {
                log.info("Repository updated. Clearing cache...");
                cacheManager.clearCache();
            } else {
                log.info("No changes detected in Git repository.");
            }

        } catch (IOException | GitAPIException e) {
            log.error("Error while checking Git repository: ", e);
        }

    }

    private void cloneRepository(File repoDir) {
        try (Git git = Git.cloneRepository()
                .setURI(configProperties.getPath())
                .setDirectory(repoDir)
                .setBranch(configProperties.getDefaultLabel())
                .call()) {
            log.info("Repository cloned successfully.");
        } catch (GitAPIException e) {
            log.error("Error cloning repository: ", e);
        }
    }
}
