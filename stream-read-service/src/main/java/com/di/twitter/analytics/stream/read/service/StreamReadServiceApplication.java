package com.di.twitter.analytics.stream.read.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.di.twitter.analytics")
public class StreamReadServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamReadServiceApplication.class, args);
    }
}
