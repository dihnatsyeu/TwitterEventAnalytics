package com.di.twitter.analytics.elastic.query.client.exception;


public class SearchEngineQueryException extends RuntimeException {

    public SearchEngineQueryException(String message) {
        super(message);
    }

    public SearchEngineQueryException(String message, Throwable e) {
        super(message, e);
    }
}
