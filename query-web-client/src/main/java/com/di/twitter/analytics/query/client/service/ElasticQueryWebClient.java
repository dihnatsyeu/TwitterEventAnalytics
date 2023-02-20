package com.di.twitter.analytics.query.client.service;



import com.di.twitter.analytics.web.client.common.model.QueryWebClientRequestModel;
import com.di.twitter.analytics.web.client.common.model.QueryWebClientResponseModel;
import java.util.List;

public interface ElasticQueryWebClient {

    List<QueryWebClientResponseModel> getDataByText(QueryWebClientRequestModel requestModel);
}
