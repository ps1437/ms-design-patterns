package com.syscho.ms.ratelimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RateLimiterMain {
    public static void main(String[] args) throws InterruptedException {
        PaymentService service = new PaymentService();



        ExecutorService executorService = Executors.newFixedThreadPool(100);


        for (int i = 0; i < 10; i++) {
            executorService.submit(service::processPayment);
        }
        Thread.sleep(2000);
        executorService.shutdown();

    }
}
