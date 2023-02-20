package com.di.twitter.analytics.reactive.query.service.service;

import com.di.twitter.analytics.elastic.model.index.IndexModel;
import com.di.twitter.analytics.elastic.model.index.impl.TwitterIndexModel;
import reactor.core.publisher.Flux;

public interface ReactiveElasticQueryClient <T extends IndexModel> {
    Flux<TwitterIndexModel> getIndexModelByText(String text);
}
