package com.di.twitter.analytics.query.client.service;

import com.di.twitter.analytics.query.client.model.QueryWebClientRequestModel;
import com.di.twitter.analytics.query.client.model.QueryWebClientResponseModel;

import java.util.List;

public interface ElasticQueryWebClient {

    List<QueryWebClientResponseModel> getDataByText(QueryWebClientRequestModel requestModel);
}
