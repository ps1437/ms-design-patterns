package com.syscho.ms.circuit_breaker;

public class CircuitBreakerMain {
    public static void main(String[] args) throws InterruptedException {
        PaymentService service = new PaymentService();

        for (int i = 0; i < 10; i++) {
            service.processPayment();
            Thread.sleep(1000); // Simulate time between requests
        }
    }
}
