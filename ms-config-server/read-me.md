# Config Server

This repository contains the configuration server for our microservices architecture. The Config Server is responsible for serving centralized configuration to various microservices.

## Server Details
- **URL**: `http://localhost:8888/`
- **Service Configured**: `account-service`

## Purpose
The Config Server centralizes configuration management, allowing all services to retrieve their configurations dynamically. This ensures consistency across environments and enables changes without redeploying services.

## Use Case
- **Centralized Configuration**: Manages configurations for `account-service` and other services in a single location.
- **Dynamic Updates**: Services can refresh configurations without needing a restart.
- **Environment-Specific Configurations**: Supports different configurations for dev, test, and production environments.
- **Secure and Scalable**: Ensures configuration security and scalability by externalizing configuration settings.

## How to Use
1. Start the Config Server:
   ```bash
   mvn spring-boot:run
   ```
2. Access the configurations via REST:
    - For default profile:
      ```
      http://localhost:8888/application/default
      ```
    - For a specific profile (e.g., `dev`):
      ```
      http://localhost:8888/application/dev
      ```
    - For a specific service (`account-service`)
       ```
      http://localhost:8888/application/account-service
      ```
   - For a specific service (`account-service`) with profile (`dev`)
     ```
      http://localhost:8888/application/account-service/dev
      ```  
3. Ensure your `account-service` is configured to fetch configurations from this server by setting:
   ```properties
   spring.config.import=optional:configserver:http://localhost:8888/
   ```

