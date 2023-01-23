package com.di.twitter.analytics.kafka.admin.exception;

public class KafkaClientException extends RuntimeException {

    public KafkaClientException(String message, Throwable e) {
        super(message, e);
    }

    public KafkaClientException(String message) {
        super(message);
    }
}
