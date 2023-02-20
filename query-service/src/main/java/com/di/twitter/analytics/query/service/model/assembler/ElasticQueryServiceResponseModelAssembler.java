package com.di.twitter.analytics.query.service.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.di.twitter.analytics.elastic.model.index.impl.TwitterIndexModel;

import com.di.twitter.analytics.query.common.model.QueryServiceResponseModel;
import com.di.twitter.analytics.query.common.transformer.ElasticToResponseModelTransformer;
import com.di.twitter.analytics.query.service.api.DocumentController;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ElasticQueryServiceResponseModelAssembler extends RepresentationModelAssemblerSupport<TwitterIndexModel,
        QueryServiceResponseModel> {

    private final ElasticToResponseModelTransformer transformer;


    public ElasticQueryServiceResponseModelAssembler(final ElasticToResponseModelTransformer transformer) {
        super(DocumentController.class, QueryServiceResponseModel.class);
        this.transformer = transformer;

    }

    @Override
    public QueryServiceResponseModel toModel(TwitterIndexModel entity) {
        QueryServiceResponseModel responseModel = transformer.getResponseModel(entity);
        responseModel.add(linkTo(methodOn(DocumentController.class).getDocumentbyId(entity.getId()))
                .withSelfRel());
        responseModel.add(linkTo(DocumentController.class).withRel("documents"));
        return responseModel;
    }

    public List<QueryServiceResponseModel> toModels(List<TwitterIndexModel> models) {
        return models.stream().map(this::toModel).collect(Collectors.toList());
    }
}
