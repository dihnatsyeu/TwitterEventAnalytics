package com.di.twitter.analytics.stream.service;

import com.di.twitter.analytics.stream.service.init.StreamInitializer;
import com.di.twitter.analytics.stream.service.runner.StreamRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.di.twitter.analytics")
public class TwitterExtractionApplication implements CommandLineRunner {

    private final StreamRunner streamRunner;
    private final StreamInitializer streamInitializer;

    public static void main(String[] args) {
        SpringApplication.run(TwitterExtractionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Applications runs");
        streamInitializer.init();
        streamRunner.start();
    }
}
