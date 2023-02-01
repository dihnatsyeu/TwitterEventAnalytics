package com.di.twitter.analytics.elastic.query.client.service.impl;

import com.di.twitter.analytics.common.util.CollectionsUtil;
import com.di.twitter.analytics.elastic.model.index.impl.TwitterIndexModel;
import com.di.twitter.analytics.elastic.query.client.exception.SearchEngineQueryException;
import com.di.twitter.analytics.elastic.query.client.repository.TwitterElasticsearchQueryRepository;
import com.di.twitter.analytics.elastic.query.client.service.ElasticQueryClient;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Primary
@Service
public class TwitterElasticRepositoryQueryClient implements ElasticQueryClient<TwitterIndexModel> {

    private final TwitterElasticsearchQueryRepository queryRepository;

    @Override
    public TwitterIndexModel getIndexModelById(String id) {
        Optional<TwitterIndexModel> foundDocuments = queryRepository.findById(id);
        TwitterIndexModel indexModel = foundDocuments.orElseThrow(() ->
                new SearchEngineQueryException("No document found with id " + id));
        log.info("Found document with id {}", indexModel.getId());
        return indexModel;
    }

    @Override
    public List<TwitterIndexModel> getIndexModelByText(String text) {
        List<TwitterIndexModel> results = queryRepository.findByText(text);
        log.info("Found {} documents when searching by text {}", results.size(), text);
        return results;
    }

    @Override
    public List<TwitterIndexModel> getAllIndexModels() {
        List<TwitterIndexModel> results = CollectionsUtil.getListFromIterable(queryRepository.findAll());
        log.info("{} number of documents retrieved successfully", results.size());
        return results;
    }
}
