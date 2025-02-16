package com.syscho.ms.bulkhead;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BulkHeadMain {
    public static void main(String[] args) throws InterruptedException {
        PaymentService paymentService = new PaymentService();
        OrderService orderService = new OrderService();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(100);
        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            scheduledExecutorService.execute(()->
                    paymentService.processPayment("Order-" + finalI));
            scheduledExecutorService.execute(()->
                    orderService.placeOrder("Order-" + finalI));
            Thread.sleep(500);
        }

        scheduledExecutorService.shutdown();
        if (!scheduledExecutorService.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("Force shutting down executor...");
            scheduledExecutorService.shutdownNow();
        }
    }
}
