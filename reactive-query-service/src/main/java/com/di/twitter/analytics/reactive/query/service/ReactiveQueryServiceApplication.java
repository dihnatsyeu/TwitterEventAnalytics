package com.di.twitter.analytics.reactive.query.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.di.twitter.analytics")
@SpringBootApplication
public class ReactiveQueryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactiveQueryServiceApplication.class, args);
    }
}
