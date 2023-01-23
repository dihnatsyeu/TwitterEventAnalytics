package com.di.twitter.analytics.stream.service.runner.impl;

import com.di.twitter.analytics.app.config.TwitterExtractorConfig;
import com.di.twitter.analytics.stream.service.runner.StreamRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(prefix = "twitter-extractor", value = "enable-v2-tweets", havingValue = "true", matchIfMissing = true)
public class TwitterV2StreamRunner implements StreamRunner {

    private final TwitterExtractorConfig config;
    private final TwitterV2StreamHelper streamHelper;

    @Override
    public void start() {
        String bearerToken = config.getTwitterV2BearerToken();
        if (null != bearerToken) {
            try {
                streamHelper.setupRules(bearerToken, getRules());
                streamHelper.connectStream(bearerToken);
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Auth bearer token is required for Twitter communication");
        }
    }

    private Map<String, String> getRules() {
        List<String> keywords = config.getTwitterKeywords();
        return keywords.stream().collect(Collectors.toMap(keyword -> keyword, keyword -> "Keyword:" + keyword));
    }
}
