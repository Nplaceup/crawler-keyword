package com.dontworry.crawler.service;

import com.dontworry.crawler.dto.KeywordCallbackRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallbackService {

    private final RestTemplate localRestTemplate;

    public void send(String keyword, LocalDate crawlDate, KeywordCallbackRequest.KeywordData response, String callbackUrl) {
        try {
            KeywordCallbackRequest body = new KeywordCallbackRequest(keyword, crawlDate, response);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<KeywordCallbackRequest> entity = new HttpEntity<>(body, headers);
            localRestTemplate.postForEntity(callbackUrl, entity, Void.class);

            log.info("Callback 전송 완료 → {}", callbackUrl);
        } catch (Exception e) {
            log.error("Callback 전송 실패 [{}]: {}", callbackUrl, e.getMessage());
        }
    }
}