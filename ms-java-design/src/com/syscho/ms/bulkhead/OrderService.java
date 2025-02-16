package com.syscho.ms.bulkhead;

public class OrderService {
    private final Bulkhead bulkhead = new Bulkhead(5,1); // Separate bulkhead for orders

    public void placeOrder(String orderId) {
        bulkhead.submitTask(() -> {
            System.out.println("🛒 Placing order: " + orderId);
        },"ORDER");
    }
}
