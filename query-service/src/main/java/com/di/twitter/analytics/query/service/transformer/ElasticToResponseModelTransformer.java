package com.di.twitter.analytics.query.service.transformer;

import com.di.twitter.analytics.elastic.model.index.impl.TwitterIndexModel;
import com.di.twitter.analytics.query.service.model.QueryServiceResponseModel;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ElasticToResponseModelTransformer {

    public QueryServiceResponseModel getResponseModel(TwitterIndexModel indexModel) {
        return QueryServiceResponseModel.builder()
                .id(indexModel.getId())
                .userId(indexModel.getUserId())
                .text(indexModel.getText())
                .createdAt(indexModel.getCreatedAt())
                .build();

    }

    public List<QueryServiceResponseModel> getResponseModels(List<TwitterIndexModel> indexModels) {
        return indexModels.stream().map(this::getResponseModel)
                .collect(Collectors.toList());
    }
}
