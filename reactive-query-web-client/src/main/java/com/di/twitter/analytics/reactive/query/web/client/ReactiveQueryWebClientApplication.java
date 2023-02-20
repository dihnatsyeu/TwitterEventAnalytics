package com.di.twitter.analytics.reactive.query.web.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.di.twitter.analytics")
public class ReactiveQueryWebClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactiveQueryWebClientApplication.class, args);
    }
}
