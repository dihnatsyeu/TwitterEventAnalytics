package com.di.twitter.analytics.query.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.di.twitter.analytics")
public class QueryWebClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueryWebClientApplication.class, args);
    }
}
