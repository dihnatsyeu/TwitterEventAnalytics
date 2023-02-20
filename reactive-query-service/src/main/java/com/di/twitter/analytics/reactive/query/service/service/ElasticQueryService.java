package com.di.twitter.analytics.reactive.query.service.service;

import com.di.twitter.analytics.query.common.model.QueryServiceResponseModel;
import reactor.core.publisher.Flux;

public interface ElasticQueryService {

    Flux<QueryServiceResponseModel> getDocumentByText(String text);
}
