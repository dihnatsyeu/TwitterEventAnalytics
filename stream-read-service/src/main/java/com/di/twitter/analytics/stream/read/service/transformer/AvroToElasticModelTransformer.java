package com.di.twitter.analytics.stream.read.service.transformer;

import com.di.twitter.analytics.avro.model.TwitterAvroModel;
import com.di.twitter.analytics.elastic.model.index.impl.TwitterIndexModel;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AvroToElasticModelTransformer {

    public List<TwitterIndexModel> getElasticModels(List<TwitterAvroModel> avroModels) {
        return avroModels.stream().map(avroModel -> TwitterIndexModel
                .builder()
                .id(String.valueOf(avroModel.getId()))
                .userId(avroModel.getUserId())
                .text(avroModel.getText())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(avroModel.getCreatedAt()),
                        ZoneId.systemDefault())).build()).collect(Collectors.toList());

    }
}
