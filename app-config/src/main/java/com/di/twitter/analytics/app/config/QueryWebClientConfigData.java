package com.di.twitter.analytics.app.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "query-web-client")
public class QueryWebClientConfigData {
    private QueryWebClient webClient;
    private Query queryByText;

    @Data
    public static class QueryWebClient {
        private Integer connectTimeoutMs;
        private Integer readTimeoutMs;
        private Integer writeTimeoutMs;
        private Integer maxInMemorySize;
        private String contentType;
        private String acceptType;
        private String baseUrl;
        private String serviceId;
        private List<Instance> instances;
    }

    @Data
    public static class Query {
        private String method;
        private String accept;
        private String uri;

    }

    @Data
    public static class Instance {
        private String id;
        private String host;
        private Integer port;
    }
}
