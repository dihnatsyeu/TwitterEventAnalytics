package com.di.twitter.analytics.reactive.query.web.client.service.impl;

import com.di.twitter.analytics.app.config.QueryWebClientConfigData;
import com.di.twitter.analytics.reactive.query.web.client.service.ElasticQueryWebClient;

import com.di.twitter.analytics.web.client.common.exception.QueryWebClientException;
import com.di.twitter.analytics.web.client.common.model.QueryWebClientRequestModel;
import com.di.twitter.analytics.web.client.common.model.QueryWebClientResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class TwitterElasticQueryWebClient implements ElasticQueryWebClient {

    private final WebClient webClient;

    private final QueryWebClientConfigData webClientConfigData;

    public TwitterElasticQueryWebClient(@Qualifier("webClient") WebClient client,
            QueryWebClientConfigData clientConfigData) {
        this.webClient = client;
        this.webClientConfigData = clientConfigData;
    }

    @Override
    public Flux<QueryWebClientResponseModel> getDataByText(QueryWebClientRequestModel requestModel) {
        return getWebClient(requestModel)
                .bodyToFlux(QueryWebClientResponseModel.class);
    }

    private WebClient.ResponseSpec getWebClient(QueryWebClientRequestModel requestModel) {
        return webClient
                .method(HttpMethod.valueOf(webClientConfigData.getQueryByText().getMethod()))
                .uri(webClientConfigData.getQueryByText().getUri())
                .accept(MediaType.valueOf(webClientConfigData.getQueryByText().getAccept()))
                .body(BodyInserters.fromPublisher(Mono.just(requestModel), createParameterizedTypeReference()))
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.equals(HttpStatus.UNAUTHORIZED),
                        clientResponse -> Mono.just(new BadCredentialsException("Not authenticated!")))
                .onStatus(
                        HttpStatus::is4xxClientError,
                        cr -> Mono.just(new QueryWebClientException(cr.statusCode().getReasonPhrase())))
                .onStatus(
                        HttpStatus::is5xxServerError,
                        cr -> Mono.just(new Exception(cr.statusCode().getReasonPhrase())));
    }

    private <T> ParameterizedTypeReference<T> createParameterizedTypeReference() {
        return new ParameterizedTypeReference<>() {
        };
    }
}
