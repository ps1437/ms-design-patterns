package com.syscho.ms.bulkhead;

public class PaymentService {
    private final Bulkhead bulkhead = new Bulkhead(3,1); // Limit to 3 concurrent requests

    public void processPayment(String orderId) {
        Runnable paymentRunner = getPaymentRunner(orderId);
        bulkhead.submitTask(paymentRunner, "PAYMENT");
    }

    private static Runnable getPaymentRunner(String orderId) {
        // Simulate processing time
        return () ->{
            try {
                System.out.println("💳 Processing payment for Order: " + orderId);
                Thread.sleep(2000); // Simulate processing time
                System.out.println("✅ Payment successful for Order: " + orderId);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }
}
