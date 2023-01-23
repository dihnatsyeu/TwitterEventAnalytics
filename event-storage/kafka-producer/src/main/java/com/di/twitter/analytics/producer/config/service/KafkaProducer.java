package com.di.twitter.analytics.producer.config.service;

import java.io.Serializable;
import org.apache.avro.specific.SpecificRecordBase;

public interface KafkaProducer<K extends Serializable, V extends SpecificRecordBase> {
    void send(String topicName, K value, V message);
}
