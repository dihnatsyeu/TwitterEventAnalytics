package com.di.twitter.analytics.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "query-service")
public class QueryServiceConfigData {
    private String version;
    private Long backPressureDelayMs;
}
