package com.di.twitter.analytics.elastic.index.client.service.impl;

import com.di.twitter.analytics.app.config.ElasticConfigData;
import com.di.twitter.analytics.elastic.index.client.service.ElasticIndexClient;
import com.di.twitter.analytics.elastic.index.client.util.ElasticIndexUtil;
import com.di.twitter.analytics.elastic.model.index.impl.TwitterIndexModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "elastic-config.is-repository", havingValue = "false")
@Service
public class TwitterElasticIndexClient implements ElasticIndexClient<TwitterIndexModel> {

    private final ElasticConfigData elasticConfigData;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticIndexUtil<TwitterIndexModel> elasticIndexUtil;

    @Override
    public List<String> save(List<TwitterIndexModel> documents) {
        List<IndexQuery> indexQueries = elasticIndexUtil.getIndexQueries(documents);
        List<String> documentIds = elasticsearchOperations.bulkIndex(indexQueries,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        log.info("Documents with id {} successfully saved within type {}", documentIds,
                TwitterIndexModel.class.getName());
        return documentIds;
    }
}
