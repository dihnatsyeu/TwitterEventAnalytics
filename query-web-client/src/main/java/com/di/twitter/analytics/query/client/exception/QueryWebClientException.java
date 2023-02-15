package com.di.twitter.analytics.query.client.exception;

public class QueryWebClientException extends RuntimeException {

    public QueryWebClientException() {
        super();
    }
    public QueryWebClientException(String message) {
        super(message);
    }

    public QueryWebClientException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
