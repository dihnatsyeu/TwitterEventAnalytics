package com.di.twitter.analytics.query.service.model;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryServiceRequestModel {
    private String id;
    @NotEmpty
    private String text;
}
