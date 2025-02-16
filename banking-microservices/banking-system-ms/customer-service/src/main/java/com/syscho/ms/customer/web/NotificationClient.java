package com.syscho.ms.customer.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "notification-service", url = "http://localhost:8083/notifications")
public interface NotificationClient {

    @PostMapping("/send")
    void sendNotification(@RequestParam String email, @RequestParam String message);
}
