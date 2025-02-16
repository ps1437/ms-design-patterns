package com.syscho.ms.saga.ordermanagement;

import java.util.ArrayList;
import java.util.List;

/**
 * SagaOrchestrator manages a sequence of operations ensuring transactional consistency.
 * - If any step fails, it executes rollback actions.
 */
public class SagaOrchestrator {
    private final List<Runnable> steps = new ArrayList<>();
    private final List<Runnable> compensations = new ArrayList<>();

    /**
     * Adds a step to the Saga workflow along with a rollback function.
     * @param step The main operation.
     * @param compensation The rollback operation if the step fails.
     */
    public void addStep(Runnable step, Runnable compensation) {
        steps.add(step);
        compensations.add(0, compensation);
    }

    /**
     * Executes the Saga workflow.
     * - If a step fails, rollbacks are triggered.
     */
    public void execute() {
        int stepIndex = 0;
        try {
            for (; stepIndex < steps.size(); stepIndex++) {
                steps.get(stepIndex).run();
            }
            System.out.println("✅ Saga Completed Successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error occurred! Rolling back transactions...");
            for (int i = 0; i < stepIndex; i++) {
                compensations.get(i).run();
            }
        }
    }
}
