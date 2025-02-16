# Orchestration-Based Saga Pattern in Java

## Introduction
The **Saga Pattern** ensures **data consistency across multiple microservices** by handling distributed transactions using a sequence of **compensable steps**. Instead of using **ACID transactions**, it allows rollback actions if a failure occurs.

This implementation follows the **Orchestration-Based Saga**, where a **central SagaOrchestrator** manages the execution of steps and rollbacks.

## Features
- ✅ **Ensures consistency** across microservices without distributed transactions
- ✅ **Automatic rollback** if any step fails
- ✅ **Lightweight implementation** using pure Java (no external libraries)
- ✅ **Easily extendable** by adding new steps

## Use Case: **E-Commerce Order Processing**
### **Workflow Steps:**
1️⃣ **Create Order** → 2️⃣ **Process Payment** → 3️⃣ **Update Inventory** → 4️⃣ **Send Order Confirmation Email**

🔹 **If a step fails, compensating (rollback) actions are triggered:**
- If **payment fails**, order is **canceled**.
- If **inventory update fails**, payment is **refunded**.
- If **email fails**, order still completes but logs an error.
