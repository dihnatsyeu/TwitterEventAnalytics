package com.di.twitter.analytics.stream.service.transformer;

import org.springframework.stereotype.Component;
import twitter4j.Status;

@Component
public class TwitterStatusToAvroTransformer {

    public com.di.twitter.analytics.avro.model.TwitterAvroModel getTwitterAvroModelFromStatus(Status status) {
        return com.di.twitter.analytics.avro.model.TwitterAvroModel.newBuilder()
                .setId(status.getId())
                .setUserId(status.getUser().getId())
                .setText(status.getText())
                .setCreatedAt(status.getCreatedAt().getTime())
                .build();

    }
}
