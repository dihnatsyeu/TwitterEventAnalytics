package com.di.twitter.analytics.stream.read.service.consumer.impl;

import com.di.twitter.analytics.app.config.KafkaConfigData;
import com.di.twitter.analytics.app.config.KafkaConsumerConfigData;
import com.di.twitter.analytics.avro.model.TwitterAvroModel;
import com.di.twitter.analytics.elastic.index.client.service.ElasticIndexClient;
import com.di.twitter.analytics.elastic.model.index.impl.TwitterIndexModel;
import com.di.twitter.analytics.kafka.admin.client.KafkaAdminClient;
import com.di.twitter.analytics.stream.read.service.consumer.KafkaConsumer;
import com.di.twitter.analytics.stream.read.service.transformer.AvroToElasticModelTransformer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TwitterKafkaConsumer implements KafkaConsumer<Long, TwitterAvroModel> {

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaAdminClient kafkaAdminClient;
    private final KafkaConfigData kafkaConfigData;
    private final KafkaConsumerConfigData consumerConfigData;
    private final AvroToElasticModelTransformer transformer;
    private final ElasticIndexClient<TwitterIndexModel> elasticIndexClient;


    @EventListener
    public void onAppStarted(ApplicationStartedEvent startedEvent) {
        kafkaAdminClient.checkTopicsCreated();
        log.info("Topic with name {} is ready for operations", kafkaConfigData.getTopicNamesToCreate());
        kafkaListenerEndpointRegistry.getListenerContainer(consumerConfigData.getConsumerGroupId()).start();
    }

    @Override
    @KafkaListener(id = "#{kafka-consumer-config.consumer-group-id}", topics = {"${kafka-config.topic-name}"})
    public void receive(
            @Payload List<TwitterAvroModel> messages,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<Integer> keys,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
            @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of messages received with keys {}, partitions {} and offsets {},"
                + "sending it to elastic: Thread id {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString(),
                Thread.currentThread().getId());
        List<TwitterIndexModel> twitterIndexModels = transformer.getElasticModels(messages);
        List<String> documentIds = elasticIndexClient.save(twitterIndexModels);
        log.info("Documents with ids {} saved to Elasticsearch", documentIds.toArray());



    }
}
