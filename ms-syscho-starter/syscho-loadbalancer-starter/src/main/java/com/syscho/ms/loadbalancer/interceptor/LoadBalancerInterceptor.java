package com.syscho.ms.loadbalancer.interceptor;

import com.syscho.ms.loadbalancer.LoadBalancer;
import com.syscho.ms.loadbalancer.model.ServerInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class LoadBalancerInterceptor implements ClientHttpRequestInterceptor {

    private final LoadBalancer loadBalancer;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        List<ServerInstance> serverInstances = Arrays.asList(
                new ServerInstance("localhost:8081", true, 0),
                new ServerInstance("localhost:8082", true, 0)
        );

        ServerInstance selectedServerInstance = loadBalancer.selectServer(serverInstances);
        if (selectedServerInstance != null) {
            URI newUri = URI.create("http://" + selectedServerInstance.getUri() + request.getURI().getPath());
            HttpRequest modifiedRequest = new HttpRequestWrapper(request, newUri);
            return execution.execute(modifiedRequest, body);
        }

        return execution.execute(request, body);
    }

    private record HttpRequestWrapper(HttpRequest request, URI newUri) implements HttpRequest {

        @Override
        public HttpMethod getMethod() {
            return request.getMethod();
        }

        @Override
        public URI getURI() {
            return newUri;
        }

        @Override
        public Map<String, Object> getAttributes() {
            return request.getAttributes();
        }

        @Override
        public HttpHeaders getHeaders() {
            return request.getHeaders();
        }
    }
}
