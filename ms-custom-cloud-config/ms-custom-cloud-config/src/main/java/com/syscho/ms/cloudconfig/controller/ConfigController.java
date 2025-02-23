package com.syscho.ms.cloudconfig;

import com.syscho.ms.cloudconfig.loader.FileLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class ConfigController {

    private final FileLoader fileLoader;

    public ConfigController(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    @GetMapping("/{filename}/{profile}")
    public Map<String, Object> getConfig(@PathVariable String filename,@PathVariable String profile) throws IOException {
        return fileLoader.loadFile(filename,profile);
    }
}
