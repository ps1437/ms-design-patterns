package com.syscho.ms.circuit_breaker;

import java.time.Duration;

/**
 * PaymentService class simulates a payment processing system with a custom Circuit Breaker.
 * <p>
 * This class implements a fail-safe mechanism where:
 * - If a service call fails multiple times, it "opens" the circuit to prevent further failures.
 * - After a cooldown period, it allows limited requests ("half-open" state) to check recovery.
 * - If a request succeeds, it resets the failure count and "closes" the circuit.
 * </p>
 */
public class PaymentService {

    // Circuit Breaker with a failure threshold of 3 and reset time of 10 seconds.
    private final CustomCircuitBreaker circuitBreaker = new CustomCircuitBreaker(3, Duration.ofSeconds(10));

    /**
     * Processes a payment request while integrating a circuit breaker.
     * <p>
     * If the circuit breaker is "OPEN", new requests are blocked.
     * If the request succeeds, the failure count is reset.
     * If the request fails, the failure count increases, and after reaching the threshold,
     * the circuit breaker moves to "OPEN" state.
     * </p>
     */
    public void processPayment() {
        // Check if the circuit breaker allows requests
        if (!circuitBreaker.allowRequest()) {
            System.out.println("Request blocked due to circuit breaker being OPEN.");
            return;
        }

        try {
            // Simulate a service call with a 30% success rate
            if (Math.random() > 0.7) {
                throw new RuntimeException("Service failed!");
            }
            System.out.println("Payment processed successfully.");
            circuitBreaker.onSuccess(); // Reset failure count on success
        } catch (Exception e) {
            System.out.println("Payment failed: " + e.getMessage());
            circuitBreaker.onFailure(); // Increase failure count on failure
        }
    }
}
