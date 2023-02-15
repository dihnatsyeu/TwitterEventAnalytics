package com.di.twitter.analytics.query.client.model;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryWebClientRequestModel {
    private String id;
    @NotEmpty
    private String text;
}
