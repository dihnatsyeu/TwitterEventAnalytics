package com.di.twitter.analytics.elastic.index.client.service;

import com.di.twitter.analytics.elastic.model.index.IndexModel;
import java.util.List;

public interface ElasticIndexClient<T extends IndexModel> {
    List<String> save(List<T> documents);
}
