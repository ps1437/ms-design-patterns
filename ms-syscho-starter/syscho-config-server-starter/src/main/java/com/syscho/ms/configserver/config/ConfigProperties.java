package com.syscho.ms.configserver.config;

import com.syscho.ms.configserver.loader.enums.ConfigProviderEnum;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@ConfigurationProperties(prefix = "config.server")
@Getter
@Setter
@Slf4j
public class ConfigProperties {
    private ConfigProviderEnum provider;
    private String path;
    private String folderPath;
    private String defaultLabel;
    private boolean refreshEnabled;

    private Path directoryPath;

    @PostConstruct
    private void derivePath() {
        this.directoryPath = PathResolver.resolve(provider, path, folderPath);
    }


    private static class PathResolver {
        static Path resolve(ConfigProviderEnum provider, String path, String folderPath) {
            return switch (provider) {
                case FILESYSTEM -> Path.of(path);
                case GITHUB -> getClonedPath(path, folderPath);
            };
        }

        private static Path getClonedPath(String gitUrl, String folderPath) {
            String repoName = extractRepoName(gitUrl);
            if (repoName == null) {
                throw new IllegalArgumentException("Invalid Git repository URL format: " + gitUrl);
            }
            return Paths.get(System.getProperty("user.home"), repoName, folderPath);
        }

        private static String extractRepoName(String gitUrl) {
            if (gitUrl == null || !gitUrl.endsWith(".git")) {
                return null;
            }
            return gitUrl.substring(gitUrl.lastIndexOf('/') + 1, gitUrl.length() - 4);
        }
    }
}
