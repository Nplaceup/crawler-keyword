package com.dontworry.crawler.service;

import com.dontworry.crawler.config.NaverApiConfig;
import com.dontworry.crawler.dto.KeywordCallbackRequest;
import com.dontworry.crawler.util.NaverSignatureUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordCrawlingService {

    private final NaverApiConfig naverApiConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    private static final String URI    = "/keywordstool";
    private static final String METHOD = "GET";

    public KeywordCallbackRequest.KeywordData crawl(String keyword) {
        log.info("[KeywordCrawlingService] 네이버 키워드 조회 시작 keyword={}", keyword);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String signature = NaverSignatureUtil.generate(
                METHOD, URI, timestamp, naverApiConfig.getSecretKey());

        String url = naverApiConfig.getBaseUrl() + URI
                + "?hintKeywords=" + keyword
                + "&showDetail=1";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Timestamp", timestamp);
        headers.set("X-API-KEY",   naverApiConfig.getApiKey());
        headers.set("X-Customer",  naverApiConfig.getCustomerId());
        headers.set("X-Signature", signature);
        headers.set("Accept",      "application/json");
        headers.remove(HttpHeaders.CONTENT_TYPE);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            log.error("네이버 API 호출 실패 [{}]: {}", keyword, e.getMessage(), e);
            return null;
        }

        randomDelay();
        KeywordCallbackRequest.KeywordData parsed = parseResponse(response.getBody(), keyword);
        log.info("[KeywordCrawlingService] 네이버 키워드 조회 완료 keyword={}, hasResponse={}",
                keyword, parsed != null);
        return parsed;
    }

    private KeywordCallbackRequest.KeywordData parseResponse(String body, String keyword) {
        try {
            return mapper.readValue(body, KeywordCallbackRequest.KeywordData.class);
        } catch (Exception e) {
            log.error("응답 파싱 실패 [{}]: {}", keyword, e.getMessage());
            return null;
        }
    }

    private void randomDelay() {
        sleep(ThreadLocalRandom.current().nextLong(800, 1001));
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
