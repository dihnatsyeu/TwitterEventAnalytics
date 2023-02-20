package com.di.twitter.analytics.reactive.query.service.service.impl;

import com.di.twitter.analytics.app.config.QueryServiceConfigData;
import com.di.twitter.analytics.elastic.model.index.impl.TwitterIndexModel;
import com.di.twitter.analytics.reactive.query.service.repository.ElasticQueryRepository;
import com.di.twitter.analytics.reactive.query.service.service.ReactiveElasticQueryClient;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Slf4j
@Service
public class TwitterReactiveElasticQueryClient implements ReactiveElasticQueryClient<TwitterIndexModel> {

    private final ElasticQueryRepository elasticQueryRepository;
    private final QueryServiceConfigData queryServiceConfigData;


    @Override
    public Flux<TwitterIndexModel> getIndexModelByText(String text) {
        log.info("Getting data from elasticsearch for text {}", text);
        return elasticQueryRepository.findByText(text)
                .delayElements(Duration.ofMillis(queryServiceConfigData.getBackPressureDelayMs()));
    }
}
