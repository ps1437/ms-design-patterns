package com.syscho.ms.circuit_breaker;

import java.time.Duration;
import java.time.Instant;

public class CustomCircuitBreaker {
    private enum State { CLOSED, OPEN, HALF_OPEN }

    private State state = State.CLOSED;
    private int failureCount = 0;
    private final int failureThreshold;
    private final Duration resetTimeout;
    private Instant lastFailureTime;

    public CustomCircuitBreaker(int failureThreshold, Duration resetTimeout) {
        this.failureThreshold = failureThreshold;
        this.resetTimeout = resetTimeout;
    }

    public synchronized boolean allowRequest() {
        if (state == State.OPEN) {
            if (Instant.now().isAfter(lastFailureTime.plus(resetTimeout))) {
                state = State.HALF_OPEN; // Allow limited requests
                return true;
            }
            return false;
        }
        return true;
    }

    public synchronized void onSuccess() {
        failureCount = 0;
        // Reset circuit on success
        state = State.CLOSED;
    }

    public synchronized void onFailure() {
        failureCount++;
        lastFailureTime = Instant.now();

        if (failureCount >= failureThreshold) {
            state = State.OPEN;
            System.out.println("Circuit Breaker OPEN: Too many failures!");
        }
    }
}
