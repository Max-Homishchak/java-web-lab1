package com.example.spacecatsmarket.config;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Slf4j
@Configuration
public class RestClientConfiguration {

    private final int responseTimeout;

    public RestClientConfiguration(@Value("${application.restclient.response-timeout:1000}") int responseTimeout) {
        this.responseTimeout = responseTimeout;
    }

    @Bean("paymentRestClient")
    public RestClient restClient() {
        return RestClient.builder()
            .requestFactory(getClientHttpRequestFactory(responseTimeout))
            .build();
    }

    @Bean("productRestClient")
    public RestClient productRestClient() {
        return RestClient.builder()
                .requestFactory(getClientHttpRequestFactory(responseTimeout))
                .build();
    }

    private static ClientHttpRequestFactory getClientHttpRequestFactory(int responseTimeout) {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
            .withReadTimeout(Duration.ofMillis(responseTimeout));
        return ClientHttpRequestFactories.get(settings);
    }
}
