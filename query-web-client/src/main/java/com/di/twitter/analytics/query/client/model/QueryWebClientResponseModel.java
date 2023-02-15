package com.di.twitter.analytics.query.client.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryWebClientResponseModel {
    private String id;
    private Long userId;
    private String text;
    private LocalDateTime createdAt;
}
