package com.di.twitter.analytics.stream.service.listener;

import com.di.twitter.analytics.app.config.KafkaConfigData;
import com.di.twitter.analytics.producer.config.service.KafkaProducer;
import com.di.twitter.analytics.stream.service.transformer.TwitterStatusToAvroTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Slf4j
@Component
@RequiredArgsConstructor
public class TwitterStatusListener extends StatusAdapter {

    private final KafkaConfigData kafkaConfigData;
    private final KafkaProducer<Long, com.di.twitter.analytics.avro.model.TwitterAvroModel> kafkaProducer;
    private final TwitterStatusToAvroTransformer modelTransformer;

    @Override
    public void onStatus(Status status) {
        log.info("Received status text {} sending to kafka topic {}", status.getText(), kafkaConfigData.getTopicName());
        com.di.twitter.analytics.avro.model.TwitterAvroModel twitterAvroModel = modelTransformer.getTwitterAvroModelFromStatus(status);
        kafkaProducer.send(kafkaConfigData.getTopicName(), twitterAvroModel.getUserId(), twitterAvroModel);
    }
}
