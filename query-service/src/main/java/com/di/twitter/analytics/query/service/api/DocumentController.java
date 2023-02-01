package com.di.twitter.analytics.query.service.api;

import com.di.twitter.analytics.query.service.model.QueryServiceRequestModel;
import com.di.twitter.analytics.query.service.model.QueryServiceResponseModel;
import com.di.twitter.analytics.query.service.service.ElasticQueryService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/document")
public class DocumentController {

    private final ElasticQueryService queryService;

    @GetMapping("/")
    public ResponseEntity<List<QueryServiceResponseModel>> getAllDocuments() {
        List<QueryServiceResponseModel> modelList = queryService.getAllDocuments();
        log.info("Returning {} documents", modelList.size());
        return ResponseEntity.ok(modelList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QueryServiceResponseModel> getDocumentbyId(@PathVariable @NotEmpty String id) {
        QueryServiceResponseModel responseModel = queryService.getDocumentById(id);
        log.info("Returning document with id {} ", responseModel.getId());
        return ResponseEntity.ok(responseModel);
    }

    @PostMapping("/text")
    ResponseEntity<List<QueryServiceResponseModel>> getDocumentsByText(@Valid
            @RequestBody QueryServiceRequestModel requestModel) {
        List<QueryServiceResponseModel> modelList = queryService.getDocumentsByText(requestModel.getText());
        log.info("Found {} records when searching by text: {}", modelList.size(), requestModel.getText());
        return ResponseEntity.ok(modelList);
    }
}
