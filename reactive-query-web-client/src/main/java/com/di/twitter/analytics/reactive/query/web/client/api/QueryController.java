package com.di.twitter.analytics.reactive.query.web.client.api;

import com.di.twitter.analytics.reactive.query.web.client.service.ElasticQueryWebClient;
import com.di.twitter.analytics.web.client.common.model.QueryWebClientRequestModel;
import com.di.twitter.analytics.web.client.common.model.QueryWebClientResponseModel;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Slf4j
@Controller
public class QueryController {

    private final ElasticQueryWebClient elasticQueryWebClient;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("queryWebClientRequestModel", QueryWebClientRequestModel.builder().build());
        return "home";
    }

    @PostMapping("/query-by-text")
    public String queryByText(@Valid QueryWebClientRequestModel requestModel,
            Model model) {
        log.info("Querying with text {}", requestModel.getText());
        Flux<QueryWebClientResponseModel> responseModel = elasticQueryWebClient.getDataByText(requestModel);
        responseModel = responseModel.log();
        IReactiveDataDriverContextVariable reactiveData =
                new ReactiveDataDriverContextVariable(responseModel,1);
        model.addAttribute("queryWebClientResponseModels", reactiveData);
        model.addAttribute("searchText", requestModel.getText());
        model.addAttribute("queryWebClientResponseModel", QueryWebClientRequestModel.builder().build());
        return "home";
    }
}
