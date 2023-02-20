package com.di.twitter.analytics.reactive.query.service.service.impl;

import com.di.twitter.analytics.elastic.model.index.impl.TwitterIndexModel;
import com.di.twitter.analytics.query.common.model.QueryServiceResponseModel;
import com.di.twitter.analytics.query.common.transformer.ElasticToResponseModelTransformer;
import com.di.twitter.analytics.reactive.query.service.service.ElasticQueryService;
import com.di.twitter.analytics.reactive.query.service.service.ReactiveElasticQueryClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Slf4j
@Service
public class TwitterElasticQueryService implements ElasticQueryService  {

    private final ReactiveElasticQueryClient<TwitterIndexModel> reactiveElasticQueryClient;

    private final ElasticToResponseModelTransformer modelTransformer;


    @Override
    public Flux<QueryServiceResponseModel> getDocumentByText(String text) {
        log.info("Querying reactive elasticsearch for text {}", text);
        return reactiveElasticQueryClient.getIndexModelByText(text)
                .map(modelTransformer::getResponseModel);
    }
}
