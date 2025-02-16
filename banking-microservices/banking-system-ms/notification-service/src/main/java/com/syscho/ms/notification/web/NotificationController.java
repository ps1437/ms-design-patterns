package com.syscho.ms.notification.web;

import com.syscho.ms.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public String sendNotification(@RequestParam String email, @RequestParam String message) {
        notificationService.sendNotification(email, message);
        return "Notification Sent!";
    }
}
