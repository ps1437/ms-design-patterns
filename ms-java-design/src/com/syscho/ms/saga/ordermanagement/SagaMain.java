package com.syscho.ms.saga.ordermanagement;

public class SagaMain {
    public static void main(String[] args) {
        SagaOrchestrator saga = new SagaOrchestrator();

        OrderService orderService = new OrderService();
        PaymentService paymentService = new PaymentService();
        InventoryService inventoryService = new InventoryService();
        NotificationService notificationService = new NotificationService();

        // Define Saga Steps & Compensations
        saga.addStep(
                orderService::createOrder,
                orderService::cancelOrder
        );
        saga.addStep(
                paymentService::processPayment,
                paymentService::refundPayment
        );
        saga.addStep(
                inventoryService::updateInventory,
                inventoryService::restoreInventory
        );
        saga.addStep(
                notificationService::sendOrderConfirmation,
                notificationService::logEmailFailure
        );

        // Execute the Saga Workflow
        saga.execute();
    }
}
