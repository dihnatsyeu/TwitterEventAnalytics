package com.di.twitter.analytics.reactive.query.service.api;

import com.di.twitter.analytics.query.common.model.QueryServiceRequestModel;
import com.di.twitter.analytics.query.common.model.QueryServiceResponseModel;
import com.di.twitter.analytics.reactive.query.service.service.ElasticQueryService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/documents")
public class ElasticDocumentController {

    private final ElasticQueryService elasticQueryService;

    @PostMapping(value = "/get-doc-by-text",
    produces = MediaType.TEXT_EVENT_STREAM_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public Flux<QueryServiceResponseModel> getDocumentByText(
            @RequestBody @Valid QueryServiceRequestModel requestModel) {
        Flux<QueryServiceResponseModel> responseModelFlux =
                elasticQueryService.getDocumentByText(requestModel.getText());
        responseModelFlux = responseModelFlux.log();
        return responseModelFlux;
    }
}
