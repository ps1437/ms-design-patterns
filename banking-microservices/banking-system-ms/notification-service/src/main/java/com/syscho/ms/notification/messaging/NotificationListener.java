package com.syscho.ms.notification.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    @KafkaListener(topics = "notifications", groupId = "notification_group")
    public void consumeNotification(String message) {
        System.out.println("Received Notification: " + message);
    }
}
