package com.syscho.ms.cloudconfig.loader;

public enum ConfigSourceType {

    GITHUB, FILESYSTEM;

    public static ConfigSourceType fromString(String value) {
        for (ConfigSourceType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown config source type: " + value);
    }
}
