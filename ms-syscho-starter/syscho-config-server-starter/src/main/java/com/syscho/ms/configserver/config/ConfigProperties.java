package com.syscho.ms.configserver.config;

import com.syscho.ms.configserver.loader.enums.ConfigProviderEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config.server")
@Getter
@Setter
public class ConfigProperties {
    private ConfigProviderEnum provider;
    private String path;
    private String folderPath;
    private String defaultLabel;
    private boolean refreshEnabled;
}
