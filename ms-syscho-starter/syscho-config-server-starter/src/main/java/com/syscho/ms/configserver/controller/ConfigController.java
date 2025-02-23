package com.syscho.ms.configserver.controller;

import com.syscho.ms.configserver.loader.FileLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ConfigController {

    private final FileLoader fileLoader;

    @GetMapping("/{filename}/{profile}")
    public Map<String, Object> getProfileConfig(
            @PathVariable String filename,
            @PathVariable String profile) throws IOException {
        return fileLoader.loadFile(filename, profile);
    }


    @GetMapping("/{filename}")
    public Map<String, Object> getConfig(@PathVariable String filename) throws IOException {
        return fileLoader.loadFile(filename);
    }

    @GetMapping("/refresh")
    public ResponseEntity<String> refresh() {
        fileLoader.clearCache();
        return ResponseEntity.ok("success");
    }

}

