package com.syscho.ms.registry.client.config;

import com.syscho.ms.registry.client.model.ServiceInstance;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * ServiceRegistryConfiguration is responsible for registering the service instance
 * with the service registry when the application starts and deregistering it when
 * the application shuts down.
 * <p>
 * This component listens to the application context refresh event, which is fired
 * when the Spring context is fully initialized. It registers the service instance
 * by sending a POST request to the service registry. On application shutdown,
 * it sends a DELETE request to deregister the service instance.
 * </p>
 *
 * <p>
 * The instanceId is randomly generated to uniquely identify the service instance
 * within the registry.
 * </p>
 */
public class ServiceRegistryConfiguration implements ApplicationListener<ContextRefreshedEvent>, DisposableBean {

    private final RestTemplate restTemplate;
    private final String instanceId;
    public final ServiceInstance serviceInstance;

    @Value("${service-registry.url}")
    private String registryBaseUrl;

    /**
     * Constructor that initializes the service registry configuration.
     *
     * @param restTemplate The {@link RestTemplate} used to make HTTP requests to the registry.
     * @param serviceInstance The {@link ServiceInstance} to be registered and deregistered.
     */
    public ServiceRegistryConfiguration(RestTemplate restTemplate, ServiceInstance serviceInstance) {
        this.restTemplate = restTemplate;
        this.instanceId = UUID.randomUUID().toString();
        this.serviceInstance = serviceInstance;
    }

    /**
     * This method is triggered when the application context is refreshed, indicating
     * that the Spring application context is fully initialized. It registers the
     * service instance with the service registry by sending a POST request.
     *
     * @param event The context refreshed event.
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String registryUrl = registryBaseUrl + "/register";
        restTemplate.postForEntity(registryUrl, serviceInstance, String.class);
    }

    /**
     * This method is called when the application context is destroyed, allowing the
     * service instance to be deregistered from the registry. It sends a DELETE request
     * to remove the service instance from the registry.
     */
    @Override
    public void destroy() {
        String deregisterUrl = registryBaseUrl + "/deregister/" + instanceId;
        restTemplate.delete(deregisterUrl);
    }
}
