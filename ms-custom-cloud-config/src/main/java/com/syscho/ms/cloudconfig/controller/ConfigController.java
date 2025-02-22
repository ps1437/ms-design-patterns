package com.syscho.ms.cloudconfig.controller;

import com.syscho.ms.cloudconfig.loader.FileLoader;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@Tag(name = "Config Controller", description = "APIs for fetching configuration files")
@RequiredArgsConstructor
public class ConfigController {

    private final FileLoader fileLoader;

    @Operation(summary = "Get Configuration with Profile", description = "Fetch configuration based on filename and profile")
    @GetMapping("/{filename}/{profile}")
    public Map<String, Object> getProfileConfig(
            @PathVariable String filename,
            @PathVariable String profile) throws IOException {
        return fileLoader.loadFile(filename, profile);
    }


    @Operation(summary = "Get Default Configuration", description = "Fetch configuration file without specifying a profile")
    @GetMapping("/{filename}")
    public Map<String, Object> getConfig(@PathVariable String filename) throws IOException {
        return fileLoader.loadFile(filename);
    }

    @Operation(summary = "Refresh Cache")
    @GetMapping("/refresh")
    public ResponseEntity<String> refresh() {
        fileLoader.refresh();
        return ResponseEntity.ok("success");
    }

}

