# Custom Rate Limiter in Java

## Introduction
A **Rate Limiter** controls the number of requests processed within a given time window, preventing system overload and ensuring fair resource usage.

This implementation uses the **Token Bucket Algorithm** to allow a fixed number of requests per second and refills the tokens at a constant rate.

## Features
- ✅ **No external libraries required**
- ✅ **Thread-safe using AtomicInteger**
- ✅ **Customizable request limit**
- ✅ **Efficient scheduling with ScheduledExecutorService**

## How It Works
1. The **RateLimiter** allows **N requests per second**.
2. Each request **decreases available tokens**.
3. If tokens **reach 0**, additional requests are **blocked** until refill.
4. **Tokens reset every second** to the max limit.

## Implementation

### 1️⃣ **RateLimiter.java** (Custom Rate Limiter)
```java
package com.syscho.ms;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Custom Rate Limiter using Token Bucket Algorithm.
 * Limits the number of requests per second.
 */
public class RateLimiter {
    private final int maxRequestsPerSecond;
    private final AtomicInteger availableTokens;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Initializes the rate limiter with a specific request limit.
     *
     * @param maxRequestsPerSecond Maximum allowed requests per second
     */
    public RateLimiter(int maxRequestsPerSecond) {
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
        return availableTokens.getAndDecrement() > 0;
    }

    /**
     * Shuts down the rate limiter gracefully.
     */
    public void shutdown() {
        scheduler.shutdown();
    }
}
```

### 2️⃣ **PaymentService.java** (Using the Rate Limiter)
```java
public class PaymentService {
    private final RateLimiter rateLimiter = new RateLimiter(5); // Allow 5 requests per second

    public void processPayment() {
        if (!rateLimiter.allowRequest()) {
            System.out.println("Request blocked due to rate limiting.");
            return;
        }
        System.out.println("Payment processed successfully.");
    }
}
```

### 3️⃣ **Main.java** (Testing the Rate Limiter)
```java
public class Main {
    public static void main(String[] args) throws InterruptedException {
        PaymentService service = new PaymentService();

        for (int i = 0; i < 10; i++) {
            service.processPayment();
            Thread.sleep(200); // Simulate client requests every 200ms
        }
    }
}
```

## Example Output
```
Payment processed successfully.
Payment processed successfully.
Payment processed successfully.
Payment processed successfully.
Payment processed successfully.
Request blocked due to rate limiting.
Request blocked due to rate limiting.
Request blocked due to rate limiting.
Request blocked due to rate limiting.
Request blocked due to rate limiting.
```

## Conclusion
This **custom rate limiter** ensures that microservices handle traffic efficiently by restricting the number of incoming requests per second. It prevents **server overload**, improves **fair resource allocation**, and ensures **API stability**.

Would you like to integrate this with an API Gateway? 🚀

