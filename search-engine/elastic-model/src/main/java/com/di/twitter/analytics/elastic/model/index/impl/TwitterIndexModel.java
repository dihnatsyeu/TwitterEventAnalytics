package com.di.twitter.analytics.elastic.model.index.impl;

import com.di.twitter.analytics.elastic.model.index.IndexModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@Document(indexName = "#{elasticConfigData.indexName}")
public class TwitterIndexModel implements IndexModel {

    private String id;
    private Long userId;
    private String text;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
    @JsonFormat(shape = Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
    private LocalDateTime createdAt;
}
