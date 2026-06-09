package com.dontworry.crawler.controller;


import com.dontworry.crawler.dto.KeywordCallbackRequest;
import com.dontworry.crawler.dto.KeywordCrawlRequest;
import com.dontworry.crawler.service.KeywordCrawlingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/keyword")
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordCrawlingService keywordCrawlingService;

    // KeywordController.java
    @PostMapping("/crawl")
    public ResponseEntity<KeywordCallbackRequest.KeywordData> crawl(
            @RequestBody KeywordCrawlRequest request
    ) {
        log.info("[KeywordController] crawl start keyword={}", request.keyword());
        KeywordCallbackRequest.KeywordData response =
                keywordCrawlingService.crawl(request.keyword());
        log.info("[KeywordController] crawl end keyword={}, hasResponse={}",
                request.keyword(), response != null);
        return ResponseEntity.ok(response);
    }
}
