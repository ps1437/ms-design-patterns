package com.syscho.ms.configclient.apps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private Environment environment;

    @GetMapping("/test")
    public String getValue(){
        return environment.getProperty("account.service.name");
    }
}
