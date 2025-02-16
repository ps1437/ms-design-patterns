# Microservice Design Patterns

This repository demonstrates various **microservice design patterns** using **Spring Boot, Spring Cloud, and Java**. It covers essential architectural patterns such as **CQRS, API Gateway, Service Registry, Config Server, and more**.

## 📂 Project Structure

```
microservice-design-pattern/
│── banking-microservices/   # Banking-related microservices architecture
│── cqrs-ms-design/         # CQRS pattern with command & query separation
│── ms-config-server/       # Centralized configuration using Spring Cloud Config
│── ms-custom-registry/     # Custom-built service registry
│── ms-gateway/            # API Gateway for routing & security
│── ms-java-design/        # Common Java design patterns for microservices
│── ms-registry-server/    # Eureka-based service registry
│── .gitignore             # Updated to exclude unnecessary files
│── README.md              # Documentation
```

## 🚀 Features

- **CQRS (Command Query Responsibility Segregation)**
- **Spring Cloud Gateway as an API Gateway**
- **Spring Cloud Config for Centralized Configuration**
- **Eureka Service Registry for Discovery**

## 🛠️ Setup & Usage

### Prerequisites

- **Java 17+**
- **Maven**
- **Docker** *(will be added later)*

### Running the Services

1. Clone the repository:
   ```sh
   git clone https://github.com/ps1437/microservice-design-pattern.git
   cd microservice-design-pattern
   ```

2. Build the projects:
   ```sh
   mvn clean install
   ```

3. Run individual microservices:
   ```sh
   mvn spring-boot:run
   ```

4. Use **Docker Compose** (if available) to spin up services:
   ```sh
   docker-compose up -d
   ```

## 🔥 Recent Updates

- **Folder Restructure**: Enhanced project organization.
- **Updated `.gitignore`**: Removed unnecessary `target/` and `.mvn/` folders.
- **Optimized Microservices Implementations**.

## 🏗️ Contribution

Feel free to **fork, clone, and submit PRs** if you'd like to enhance the project! 🚀


---

Happy coding! 💻🚀