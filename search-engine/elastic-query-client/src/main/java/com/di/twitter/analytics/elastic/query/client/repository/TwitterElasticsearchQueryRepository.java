package com.di.twitter.analytics.elastic.query.client.repository;

import com.di.twitter.analytics.elastic.model.index.impl.TwitterIndexModel;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwitterElasticsearchQueryRepository extends ElasticsearchRepository<TwitterIndexModel, String> {

    List<TwitterIndexModel> findByText(String text);
}
