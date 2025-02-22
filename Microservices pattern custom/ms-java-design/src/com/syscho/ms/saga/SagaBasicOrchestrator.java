package com.syscho.ms.saga;

import java.util.ArrayList;
import java.util.List;

public class SagaBasicOrchestrator {

    private final List<Runnable> steps = new ArrayList<>();
    private final List<Runnable> compensations = new ArrayList<>();

    public void addStep(Runnable step, Runnable compensation) {
        steps.add(step);
        compensations.add(0, compensation);
    }

    public void execute() {
        int stepIndex = 0;
        try {
            for (; stepIndex < steps.size(); stepIndex++) {
                steps.get(stepIndex).run();
            }
        } catch (Exception e) {
            System.out.println("Error! Rolling back transactions...");
            for (int i = 0; i < stepIndex; i++) {
                compensations.get(i).run();
            }
        }
    }

    public static void main(String[] args) {
        SagaBasicOrchestrator saga = new SagaBasicOrchestrator();

        saga.addStep(
            () -> System.out.println("Step 1: Order placed"), 
            () -> System.out.println("Rollback: Order canceled")
        );
        saga.addStep(
            () -> { System.out.println("Step 2: Payment processed"); throw new RuntimeException("Payment failed!"); },
            () -> System.out.println("Rollback: Payment refunded")
        );
        saga.addStep(
            () -> System.out.println("Step 3: Inventory updated"),
            () -> System.out.println("Rollback: Inventory restored")
        );

        saga.execute();
    }
}
