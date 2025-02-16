package com.syscho.ms.accounts.controller;

import com.syscho.ms.accounts.AccountServiceConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigTestController {

    private final AccountServiceConfig config;

    public ConfigTestController(AccountServiceConfig config) {
        this.config = config;
    }

    @GetMapping
    public String getConfigValue() {
        return config.getName();  // Should return "Praveen"
    }
}
