package com.syscho.ms.cloudconfig.loader;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class GitHubFileLoader implements FileLoader {

    @Override
    public Map<String, Object> loadFile(String fileName, String profile) {
        return null;
    }

    @Override
    public Map<String, Object> loadFile(String filename) throws IOException {
        return Map.of();
    }

    @Override
    public void refresh() {

    }
}
