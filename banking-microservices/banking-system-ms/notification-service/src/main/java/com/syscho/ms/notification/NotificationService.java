package com.syscho.ms.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendNotification(String email, String message) {
        String notificationMessage = "Email: " + email + " | Message: " + message;
        kafkaTemplate.send("notifications", notificationMessage);
        System.out.println("Notification sent: " + notificationMessage);
    }
}
