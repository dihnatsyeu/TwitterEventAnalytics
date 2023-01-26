package com.di.twitter.analytics.elastic.index.client.service.impl;

import com.di.twitter.analytics.elastic.index.client.repository.TwitterElasticsearchIndexRepository;
import com.di.twitter.analytics.elastic.index.client.service.ElasticIndexClient;
import com.di.twitter.analytics.elastic.model.index.impl.TwitterIndexModel;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@ConditionalOnProperty(name = "elastic-config.is-repository", havingValue = "true", matchIfMissing = true)
@Slf4j
@RequiredArgsConstructor
public class TwitterElasticRepositoryIndexClient implements ElasticIndexClient<TwitterIndexModel> {

    private final TwitterElasticsearchIndexRepository repository;

    @Override
    public List<String> save(List<TwitterIndexModel> documents) {
        List<TwitterIndexModel> savedObjects = (List<TwitterIndexModel>)repository.saveAll(documents);
        List<String> ids = savedObjects.stream().map(TwitterIndexModel::getId).collect(Collectors.toList());
        log.info("Documents with id {} successfully saved within type {}", ids,
                TwitterIndexModel.class.getName());
        return ids;
    }
}
