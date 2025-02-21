package com.syscho.ms.cloudconfig.loader;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GitHubFileLoader implements FileLoader {

    @Override
    public Map<String, Object> loadFile(String fileName, String profile) {
        return null;
    }
}
