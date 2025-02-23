package com.syscho.ms.cloudconfig;

import com.syscho.ms.cloudconfig.loader.ConfigProviderEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file-loader")
@Getter
@Setter
public class FileLoaderProperties {
    private ConfigProviderEnum type;
    private String path;
    private String defaultLabel = "main";
    private boolean refreshEnabled = true;
}
