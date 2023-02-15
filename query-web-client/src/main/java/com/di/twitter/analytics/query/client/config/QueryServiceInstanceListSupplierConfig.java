package com.di.twitter.analytics.query.client.config;

import com.di.twitter.analytics.app.config.QueryWebClientConfigData;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Configuration
@Primary
public class QueryServiceInstanceListSupplierConfig implements ServiceInstanceListSupplier {

    private final QueryWebClientConfigData.QueryWebClient webClientConfig;

    @Override
    public String getServiceId() {
        return webClientConfig.getServiceId();
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(webClientConfig.getInstances().stream()
                .map(instance -> new DefaultServiceInstance(
                        instance.getId(),
                        getServiceId(),
                        instance.getHost(),
                        instance.getPort(),
                        false
                )).collect(Collectors.toList()));
    }
}
