# Spring Cloud Gateway Configuration Guide

## Overview
Spring Cloud Gateway is an API Gateway built on Spring Boot and Spring WebFlux. It provides features like routing, filtering, security, and monitoring for microservices architectures.

## Key Capabilities
- **Dynamic Routing:** Routes requests based on patterns, headers, and other conditions.
- **Load Balancing:** Distributes requests among microservices registered in Eureka.
- **Security & Authentication:** Integrates with OAuth2, JWT, and Spring Security.
- **Rate Limiting & Circuit Breaker:** Prevents service overload and improves fault tolerance.
- **Request Transformation:** Modifies headers, query params, and request bodies.
- **Monitoring & Observability:** Supports Spring Actuator for health checks and metrics.

---

## Configuration Properties in `application.yml`

### **Basic Gateway Configuration**
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: account-service
          uri: lb://ACCOUNT-SERVICE
          predicates:
            - Path=/accounts/**
        - id: customer-service
          uri: lb://CUSTOMER-SERVICE
          predicates:
            - Path=/customers/**
```

### **Common Properties**
| Property | Description |
|----------|------------|
| `spring.cloud.gateway.routes.id` | Unique route identifier |
| `spring.cloud.gateway.routes.uri` | Target URI for the route (e.g., `lb://SERVICE-NAME`) |
| `spring.cloud.gateway.routes.predicates` | Conditions to match requests (e.g., `Path=/customers/**`) |
| `spring.cloud.gateway.routes.filters` | Modifies requests/responses (e.g., `StripPrefix=1`) |
| `spring.cloud.gateway.globalcors` | Configures CORS settings globally |
| `spring.cloud.gateway.discovery.locator.enabled` | Enables automatic service discovery via Eureka |

### **Eureka Configuration**
```yaml
eureka:
  client:
    registerWithEureka: true
    fetchRegistry: true
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
  instance:
    preferIpAddress: true
```

### **Swagger Integration**
```yaml
springdoc:
  swagger-ui:
    urls:
      - name: Account Service
        url: /account/v3/api-docs
      - name: Customer Service
        url: /customer/v3/api-docs
```

### **Exposing Monitoring Endpoints**
```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

---

## Advanced Routing & Filters

### **Common Predicates**
| Predicate | Description |
|-----------|------------|
| `Path=/api/**` | Matches requests with the given path pattern |
| `Header=X-Request-Id, ".*"` | Matches requests containing a specific header |
| `Method=GET,POST` | Routes requests based on HTTP method |
| `Query=type, gold` | Matches requests with query parameter `type=gold` |
| `Before=2025-01-01T00:00:00Z` | Routes requests before a specific time |

### **Common Filters**
| Filter | Description |
|--------|------------|
| `AddRequestHeader=Header,Value` | Adds a header to the request |
| `RemoveRequestHeader=Header` | Removes a header from the request |
| `SetPath=/newpath` | Modifies the request path |
| `RewritePath=/old/(?<segment>.*), /new/${segment}` | Rewrites request path dynamically |
| `StripPrefix=1` | Removes path segments before forwarding |

---

## Real-World Use Cases
1. **Centralized API Gateway**: Clients interact with a single entry point instead of multiple services.
2. **Service Discovery & Load Balancing**: Uses Eureka to dynamically route requests to available instances.
3. **Request Transformation**: Adds authentication headers before forwarding requests.
4. **Security Enforcement**: Implements JWT validation and authorization checks.
5. **Rate Limiting & Circuit Breaker**: Prevents excessive load on backend services.

---
## Spring Cloud API Gateway - Useful Links & Endpoints

## 🚀 Actuator Endpoints for API Gateway

### 1️⃣ Get All Routes
```http
GET http://localhost:8085/actuator/gateway/routes
```
🔹 Returns details of all configured routes.

### 2️⃣ Get Specific Route by ID
```http
GET http://localhost:8085/actuator/gateway/routes/{routeId}
```
🔹 Replace `{routeId}` with the actual route ID to fetch details.

### 3️⃣ Refresh Routes
```http
POST http://localhost:8085/actuator/gateway/refresh
```
🔹 Forces API Gateway to reload route configurations.

### 4️⃣ Get Global Filters
```http
GET http://localhost:8085/actuator/gateway/globalfilters
```
🔹 Lists all global filters applied to API Gateway.

### 5️⃣ Get Route-Specific Filters
```http
GET http://localhost:8085/actuator/gateway/routefilters
```
🔹 Shows filters applied to individual routes.

### 6️⃣ View Service Registry (If Enabled)
```http
GET http://localhost:8085/actuator/serviceregistry
```
🔹 Displays registered services in **Eureka/Consul/Nacos**, if applicable.

---
## 🔧 Enable Actuator in `application.yml`
```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    gateway:
      enabled: true
```

For more detailed configurations, refer to the [Spring Cloud Gateway Documentation](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/).


---
## Official Documentation & References
- 🔗 [Spring Cloud Gateway Documentation](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/)
- 🔗 [Spring Cloud Eureka Documentation](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html)
- 🔗 [Spring Boot Actuator Docs](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
