package com.syscho.ms.cloudconfig.config;

import com.syscho.ms.cloudconfig.loader.ConfigProviderEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config.server")
@Getter
@Setter
public class ConfigProperties {
    private ConfigProviderEnum type;
    private String path;
    private String defaultLabel;
    private boolean refreshEnabled;
}
