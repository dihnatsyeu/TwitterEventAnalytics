package com.di.twitter.analytics.kafka.admin.config;

import com.di.twitter.analytics.app.config.KafkaConfigData;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
@AllArgsConstructor
public class KafkaAdminConfig {

    private final KafkaConfigData configData;

    @Bean
    public AdminClient adminClient() {
        return AdminClient.create(Map.of(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
                configData.getBootstrapServers()));
    }
}
