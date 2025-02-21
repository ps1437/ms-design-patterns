package com.syscho.ms.cloudconfig;

import com.syscho.ms.cloudconfig.loader.ConfigSourceType;
import com.syscho.ms.cloudconfig.loader.FileLoader;
import com.syscho.ms.cloudconfig.loader.FileSystemLoaderYML;
import com.syscho.ms.cloudconfig.loader.GitHubFileLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileLoaderConfig {

    private final GitHubFileLoader gitHubFileLoader;
    private final FileSystemLoaderYML fileSystemLoaderYML;

    @Value("${file-loader.type}")
    private String configSourceType;

    @Value("${file-loader.path}")
    private String filePath;

    public FileLoaderConfig(GitHubFileLoader gitHubFileLoader, FileSystemLoaderYML fileSystemLoaderYML) {
        this.gitHubFileLoader = gitHubFileLoader;
        this.fileSystemLoaderYML = fileSystemLoaderYML;
    }

    @Bean
    public FileLoader fileLoader() {
            ConfigSourceType sourceType = ConfigSourceType.fromString(configSourceType);

        return switch (sourceType) {
            case GITHUB -> gitHubFileLoader;
            case FILESYSTEM -> fileSystemLoaderYML;
            default -> throw new IllegalArgumentException("Unknown file loader type: " + sourceType);
        };
    }

    @Bean
    public String filePath() {
        return filePath;
    }
}
