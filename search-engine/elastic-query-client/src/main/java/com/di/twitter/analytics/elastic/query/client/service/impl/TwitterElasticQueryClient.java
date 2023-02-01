package com.di.twitter.analytics.elastic.query.client.service.impl;

import com.di.twitter.analytics.app.config.ElasticConfigData;
import com.di.twitter.analytics.app.config.ElasticQueryConfigData;
import com.di.twitter.analytics.elastic.model.index.impl.TwitterIndexModel;
import com.di.twitter.analytics.elastic.query.client.exception.SearchEngineQueryException;
import com.di.twitter.analytics.elastic.query.client.service.ElasticQueryClient;
import com.di.twitter.analytics.elastic.query.client.util.ElasticQueryUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class TwitterElasticQueryClient implements ElasticQueryClient<TwitterIndexModel> {

    private final ElasticConfigData elasticConfigData;
    private final ElasticQueryConfigData queryConfigData;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticQueryUtil<TwitterIndexModel> elasticQueryUtil;

    @Override
    public TwitterIndexModel getIndexModelById(String id) {
        Query query = elasticQueryUtil.getSearchQueryById(id);
        SearchHit<TwitterIndexModel> searchHit = elasticsearchOperations.searchOne(query,
                TwitterIndexModel.class,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        if (searchHit == null) {
            log.error("No document found with id {}", id);
            throw new SearchEngineQueryException("No document found with id " + id);
        }
        log.info("Found document with id {} when queried", searchHit.getId());
        return searchHit.getContent();
    }

    @Override
    public List<TwitterIndexModel> getIndexModelByText(String text) {
        Query query = elasticQueryUtil.getSearchQueryByFieldText(queryConfigData.getTextField(), text);
        return getModels(query, "{} of documents with text {} found", text);
    }

    private List<TwitterIndexModel> getModels(Query query, String logMessage, Object...logParams) {
        SearchHits<TwitterIndexModel> searchHits = elasticsearchOperations.search(
                query,
                TwitterIndexModel.class,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        log.info(logMessage, searchHits.getTotalHits(), logParams);
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    @Override
    public List<TwitterIndexModel> getAllIndexModels() {
        Query query = elasticQueryUtil.getSearchQueryForAll();
        return getModels(query, "{} of documents founded ");

    }
}
