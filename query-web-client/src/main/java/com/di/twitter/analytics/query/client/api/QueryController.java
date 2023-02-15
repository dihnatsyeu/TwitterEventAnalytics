package com.di.twitter.analytics.query.client.api;

import com.di.twitter.analytics.query.client.model.QueryWebClientRequestModel;
import com.di.twitter.analytics.query.client.model.QueryWebClientResponseModel;
import com.di.twitter.analytics.query.client.service.ElasticQueryWebClient;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
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
        List<QueryWebClientResponseModel> responseModels = elasticQueryWebClient.getDataByText(requestModel);
        model.addAttribute("queryWebClientResponseModels", responseModels);
        model.addAttribute("searchText", requestModel.getText());
        model.addAttribute("queryWebClientResponseModel", QueryWebClientRequestModel.builder().build());
        return "home";
    }
}
