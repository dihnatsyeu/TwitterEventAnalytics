package com.di.twitter.analytics.producer.config.service.impl;


import com.di.twitter.analytics.producer.config.service.KafkaProducer;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
@RequiredArgsConstructor
public class TwitterKafkaProducer implements KafkaProducer<Long, com.di.twitter.analytics.avro.model.TwitterAvroModel> {

    private final KafkaTemplate<Long, com.di.twitter.analytics.avro.model.TwitterAvroModel> kafkaTemplate;

    @Override
    public void send(String topicName, Long key, com.di.twitter.analytics.avro.model.TwitterAvroModel message) {
        log.info("Sending message='{}' to topic='{}'", message, topicName);
        ListenableFuture<SendResult<Long, com.di.twitter.analytics.avro.model.TwitterAvroModel>> result = kafkaTemplate.send(topicName, key, message);
        result.addCallback(getCallback());

    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer");
            kafkaTemplate.destroy();
        }
    }


    private static ListenableFutureCallback<SendResult<Long, com.di.twitter.analytics.avro.model.TwitterAvroModel>> getCallback() {
        return new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Was unable to send twitter message ", ex);
            }

            @Override
            public void onSuccess(SendResult<Long, com.di.twitter.analytics.avro.model.TwitterAvroModel> result) {
                RecordMetadata metadata = result.getRecordMetadata();
                log.info(
                        "Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp(),
                        System.nanoTime());
            }
        };
    }
}
