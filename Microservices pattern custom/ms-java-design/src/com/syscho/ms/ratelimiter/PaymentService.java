package com.syscho.ms.ratelimiter;

import com.syscho.ms.ratelimiter.fixed.FixedWindowRateLimiter;
import com.syscho.ms.ratelimiter.token.RateLimiterTokenBased;

public class PaymentService {
    private final RateLimiterTokenBased rateLimiterTokenBased = new RateLimiterTokenBased(5); // Allow 5 requests per second
    private final FixedWindowRateLimiter rateLimiter = new FixedWindowRateLimiter(5); // Allow 5 requests per second

    public void processPayment() {
        if (!rateLimiterTokenBased.allowRequest()) {
            System.out.println("Request blocked due to rate limiting.");
            return;
        }
        System.out.println("Payment processed successfully.");
    }

    public void processPaymentFixed() {
        if (!rateLimiter.allowRequest()) {
            System.out.println("Request blocked due to rate limiting.");
            return;
        }
        System.out.println("Payment processed successfully.");
    }
}
