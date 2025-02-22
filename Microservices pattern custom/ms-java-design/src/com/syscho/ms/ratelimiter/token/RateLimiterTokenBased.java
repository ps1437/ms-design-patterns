package com.syscho.ms.ratelimiter.token;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Custom Rate Limiter using Token Bucket Algorithm.
 * Limits the number of requests per second.
 */
public class RateLimiterTokenBased {
    private final int maxRequestsPerSecond;
    private final AtomicInteger availableTokens;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Initializes the rate limiter with a specific request limit.
     *
     * @param maxRequestsPerSecond Maximum allowed requests per second
     */
    public RateLimiterTokenBased(int maxRequestsPerSecond) {
        this.maxRequestsPerSecond = maxRequestsPerSecond;
        this.availableTokens = new AtomicInteger(maxRequestsPerSecond);

        // Refill tokens at a fixed rate
        scheduler.scheduleAtFixedRate(() -> {
            availableTokens.set(maxRequestsPerSecond); // Reset tokens every second
        }, 1, 1, TimeUnit.SECONDS);
    }

    /**
     * Attempts to acquire a token.
     * @return true if request is allowed, false if rate limit exceeded.
     */
    public boolean allowRequest() {
        int availableToken = availableTokens.getAndDecrement();
        System.out.println("current token count :" +availableToken);
        return availableToken > 0;
    }

    /**
     * Shuts down the rate limiter gracefully.
     */
    public void shutdown() {
        scheduler.shutdown();
    }
}
