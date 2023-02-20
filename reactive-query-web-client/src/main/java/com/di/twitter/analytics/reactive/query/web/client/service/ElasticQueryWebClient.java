package com.di.twitter.analytics.reactive.query.web.client.service;

import com.di.twitter.analytics.web.client.common.model.QueryWebClientRequestModel;
import com.di.twitter.analytics.web.client.common.model.QueryWebClientResponseModel;
import reactor.core.publisher.Flux;

public interface ElasticQueryWebClient {

    Flux<QueryWebClientResponseModel> getDataByText(QueryWebClientRequestModel requestModel);
}
