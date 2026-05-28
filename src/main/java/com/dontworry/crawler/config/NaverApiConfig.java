package com.dontworry.crawler.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class NaverApiConfig {

    private final String baseUrl    = "https://api.searchad.naver.com";
    private final String apiKey     = "0100000000b3f69a2406a52d084bc6a276644fa555c7f6f219b3acce80502a64a49b908630";
    private final String secretKey  = "AQAAAACz9pokBqUtCEvGonZkT6VVmle7nMsCdTOTrve+GZJ47Q==";
    private final String customerId = "3167118";
}