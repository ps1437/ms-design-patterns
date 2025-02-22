package com.syscho.ms.cloudconfig.loader.enums;

public enum ConfigProviderEnum {

    GITHUB, FILESYSTEM;

    public static ConfigProviderEnum fromString(String value) {
        for (ConfigProviderEnum type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown config source type: " + value);
    }
}
