package com.di.twitter.analytics.query.service.service;

import com.di.twitter.analytics.query.service.model.QueryServiceResponseModel;
import java.util.List;

public interface ElasticQueryService {
    QueryServiceResponseModel getDocumentById(String id);
    List<QueryServiceResponseModel> getDocumentsByText(String text);
    List<QueryServiceResponseModel> getAllDocuments();
}
