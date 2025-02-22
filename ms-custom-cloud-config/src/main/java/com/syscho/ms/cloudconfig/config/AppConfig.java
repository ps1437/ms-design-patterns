package com.syscho.ms.cloudconfig.config;

import com.syscho.ms.cloudconfig.loader.enums.ConfigProviderEnum;
import com.syscho.ms.cloudconfig.loader.FileLoader;
import com.syscho.ms.cloudconfig.loader.FileSystemLoaderYML;
import com.syscho.ms.cloudconfig.loader.GitHubFileLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final GitHubFileLoader gitHubFileLoader;
    private final FileSystemLoaderYML fileSystemLoaderYML;
    private final ConfigProperties configProperties;

    @Bean
    public FileLoader fileLoader() {
        ConfigProviderEnum configType = Objects.requireNonNull(configProperties.getType(),
                "Config provider type cannot be null!");

        return switch (configType) {
            case GITHUB -> gitHubFileLoader;
            case FILESYSTEM -> fileSystemLoaderYML;
        };
    }
}
