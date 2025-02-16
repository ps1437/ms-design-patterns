# Service Registry (Eureka)

## Purpose
The Service Registry acts as a centralized directory for microservices in a distributed system. It enables dynamic service discovery, allowing services to register and discover each other without hardcoded URLs.

## Use Case
- **Service Discovery**: Enables microservices to dynamically locate each other.
- **Load Balancing**: Works with clients like Ribbon to distribute traffic among instances.
- **Fault Tolerance**: Helps in failover by providing available service instances.
- **Decoupling Services**: Removes dependency on static configuration by dynamically discovering services.

## Capabilities
- **Instance Registration & Discovery**: Services can register and be discovered dynamically.
- **Heartbeat Monitoring**: Keeps track of service health and removes unhealthy instances.
- **Zone-Based Registry**: Supports multi-region deployments with availability zones.
- **Peer-to-Peer Replication**: Can replicate data across multiple Eureka servers.

## Key Properties Explained
```yaml
server:
  port : 8761  # Defines the port where the registry runs

spring:
  application:
    name : Eureka-INDA  # Application name for this registry

eureka:
  instance:
    hostname: eureka-india  # Hostname for this Eureka instance
  client:
    registerWithEureka: true  # This registry registers itself with another Eureka instance
    fetchRegistry: true  # Enables fetching of registry information from other nodes
    serviceUrl:
      defaultZone: http://localhost:8762/eureka/  # Points to another registry instance for replication
  server:
    evictionIntervalTimerInMs: 50000  # Defines how frequently the server removes unhealthy instances
  datacenter: DS-INDIA  # Defines the datacenter name
  availabilityZones:
    DS-INDIA: india  # Defines the availability zone for this registry
```

## Useful Endpoints
- **`/apps`**: Retrieves all registered applications.
- **`/eureka/apps/{application-name}`**: Fetches instances of a specific application.
- **`/eureka/status`**: Provides the health status of the registry.
- **`/eureka/lastn/{count}`**: Displays the last `n` registered instances.
- **`/eureka/vips/{vipAddress}`**: Lists instances mapped to a VIP address.
- **`/eureka/svips/{secureVipAddress}`**: Lists instances mapped to a Secure VIP address.

## Multi-Registry & Peer-to-Peer Setup
### **Multi-Registry Use Case**
A multi-registry setup allows for better fault tolerance and geographic distribution of services. Common scenarios include:
1. **Cross-Region High Availability**: Multiple Eureka servers across different regions ensure failover support.
2. **Load Distribution**: Clients can discover the nearest service instance based on regions.
3. **Reduced Latency**: Services within a region query the nearest registry instance.

### **Peer-to-Peer Replication**
- **Each registry instance should point to other instances using `defaultZone`**.
- **Replicated instances maintain an up-to-date service registry across nodes.**

Example multi-instance configuration:
```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://eureka-server-1:8761/eureka/,http://eureka-server-2:8762/eureka/
```
This ensures both Eureka servers replicate data between each other.

## Conclusion
Implementing a service registry enhances service discovery, fault tolerance, and scalability. Using a multi-registry setup with peer-to-peer replication ensures high availability and better performance across distributed systems.

