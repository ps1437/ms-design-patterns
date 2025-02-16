package com.syscho.ms.ratelimiter.fixed;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Custom Fixed Window Rate Limiter.
 * Limits requests per a fixed time window.
 */
public class FixedWindowRateLimiter {
    private final int maxRequests;
    private final AtomicInteger requestCount;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Initializes the rate limiter with a fixed request limit per time window.
     *
     * @param maxRequests Maximum allowed requests per second
     */
    public FixedWindowRateLimiter(int maxRequests) {
        this.maxRequests = maxRequests;
        this.requestCount = new AtomicInteger(0);

        // Reset the counter every second
        scheduler.scheduleAtFixedRate(() -> {
            requestCount.set(0);
        }, 1, 1, TimeUnit.SECONDS);
    }

    /**
     * Checks if a request is allowed.
     *
     * @return true if allowed, false if rate limit exceeded.
     */
    public boolean allowRequest() {
        int currentCount = requestCount.incrementAndGet();
        System.out.println("Current Request Count: " + currentCount);

        if (currentCount <= maxRequests) {
            return true;
        } else {
            requestCount.decrementAndGet(); // Revert increment if request is rejected
            System.out.println("Request denied due to rate limiting.");
            return false;
        }
    }


    /**
     * Shuts down the rate limiter gracefully.
     */
    public void shutdown() {
        scheduler.shutdown();
    }
}
