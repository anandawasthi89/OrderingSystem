package com.myecom.oms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    WebClient webClient = WebClient.create("http://localhost:8080/");

    public WebClient getWebClient() {
        return webClient;
    }
}
