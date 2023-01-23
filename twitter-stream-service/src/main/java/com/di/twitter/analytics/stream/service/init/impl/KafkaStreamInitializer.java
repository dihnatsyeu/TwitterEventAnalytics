package com.di.twitter.analytics.stream.service.init.impl;

import com.di.twitter.analytics.app.config.KafkaConfigData;
import com.di.twitter.analytics.kafka.admin.client.KafkaAdminClient;
import com.di.twitter.analytics.stream.service.init.StreamInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaStreamInitializer implements StreamInitializer {

    private final KafkaConfigData configData;
    private final KafkaAdminClient kafkaAdminClient;

    @Override
    public void init() {
        kafkaAdminClient.createTopics();
        kafkaAdminClient.checkSchemaRegistry();
        log.info("Topics with name {} is ready for operations!", configData.getTopicNamesToCreate().toArray());
    }
}
