package com.syscho.ms.saga.ordermanagement;

public class InventoryService {
    public void updateInventory() {
        System.out.println("📦 Inventory Updated.");
        if (Math.random() > 0.7) { // Simulating failure
            throw new RuntimeException("❌ Inventory Update Failed!");
        }
    }

    public void restoreInventory() {
        System.out.println("🔄 Inventory Rollback: Restored Stock.");
    }
}
