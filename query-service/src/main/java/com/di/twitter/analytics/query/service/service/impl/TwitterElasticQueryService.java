package com.di.twitter.analytics.query.service.service.impl;

import com.di.twitter.analytics.elastic.model.index.impl.TwitterIndexModel;
import com.di.twitter.analytics.elastic.query.client.service.ElasticQueryClient;
import com.di.twitter.analytics.query.common.model.QueryServiceResponseModel;
import com.di.twitter.analytics.query.service.model.assembler.ElasticQueryServiceResponseModelAssembler;
import com.di.twitter.analytics.query.service.service.ElasticQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TwitterElasticQueryService implements ElasticQueryService {

    private final ElasticQueryServiceResponseModelAssembler assembler;
    private final ElasticQueryClient<TwitterIndexModel> queryClient;

    @Override
    public QueryServiceResponseModel getDocumentById(String id) {
        log.info("Querying elasticsearch by id {}", id);
        return assembler.toModel(queryClient.getIndexModelById(id));

    }

    @Override
    public List<QueryServiceResponseModel> getDocumentsByText(String text) {
        log.info("Querying elasticsearch by text {}", text);
        return assembler.toModels(queryClient.getIndexModelByText(text));
    }

    @Override
    public List<QueryServiceResponseModel> getAllDocuments() {
        log.info("Querying all documents from elasticsearch");
        return assembler.toModels(queryClient.getAllIndexModels());
    }
}
