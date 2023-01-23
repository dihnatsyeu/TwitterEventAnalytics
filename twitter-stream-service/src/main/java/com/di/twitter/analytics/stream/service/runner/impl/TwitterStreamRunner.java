package com.di.twitter.analytics.stream.service.runner.impl;


import com.di.twitter.analytics.app.config.TwitterExtractorConfig;
import com.di.twitter.analytics.stream.service.runner.StreamRunner;
import com.di.twitter.analytics.stream.service.listener.TwitterStatusListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import javax.annotation.PreDestroy;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(prefix = "twitter-extractor", value = "enable-v2-tweets", havingValue = "false")
public class TwitterStreamRunner implements StreamRunner {

    private final TwitterStatusListener twitterStatusListener;
    private TwitterStream twitterStream;
    private final TwitterExtractorConfig config;

    @Override
    public void start() throws TwitterException {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(twitterStatusListener);
        addFilter();
    }

    @PreDestroy
    public void shutDown() {
        if (twitterStream != null) {
            twitterStream.shutdown();
        }
    }

    private void addFilter() {
        String[] keywords = config.getTwitterKeywords().toArray(new String[0]);
        FilterQuery filterQuery = new FilterQuery(keywords);
        twitterStream.filter(filterQuery);
        log.info("Started filtering twitter stream for keyworkds");
    }
}
