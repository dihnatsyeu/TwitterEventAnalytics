package com.di.twitter.analytics.reactive.query.web.client.config;

import com.di.twitter.analytics.app.config.QueryWebClientConfigData;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@RequiredArgsConstructor
@Configuration
public class WebClientConfig {

    private final QueryWebClientConfigData.QueryWebClient webClientConfig;

    @Bean("webClient")
    WebClient webClient() {
        return WebClient.builder()
                .baseUrl(webClientConfig.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, webClientConfig.getContentType())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(getTcpClient())))
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(webClientConfig.getMaxInMemorySize()))
                .build();
    }

    private TcpClient getTcpClient() {
        return TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientConfig.getConnectTimeoutMs())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(webClientConfig.getReadTimeoutMs(),
                            TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(webClientConfig.getWriteTimeoutMs(),
                            TimeUnit.MILLISECONDS));
                });
    }
}
