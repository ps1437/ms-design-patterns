package com.syscho.ms.saga.ordermanagement;

public class PaymentService {
    public void processPayment() {
        System.out.println("💰 Payment Processed Successfully.");
        if (Math.random() > 0.7) { // Simulating random failure
            throw new RuntimeException("❌ Payment Failed!");
        }
    }

    public void refundPayment() {
        System.out.println("🔄 Payment Refunded.");
    }
}
