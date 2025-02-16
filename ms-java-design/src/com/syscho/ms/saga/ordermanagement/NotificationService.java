package com.syscho.ms.saga.ordermanagement;

public class NotificationService {
    public void sendOrderConfirmation() {
        System.out.println("📩 Order Confirmation Email Sent.");
        if (Math.random() > 0.8) { // Simulating failure
            throw new RuntimeException("❌ Email Sending Failed!");
        }
    }

    public void logEmailFailure() {
        System.out.println("⚠️ Email Sending Failed! Logged for retry.");
    }
}
