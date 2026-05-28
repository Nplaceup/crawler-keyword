package com.dontworry.crawler.controller;


import com.dontworry.crawler.dto.KeywordCallbackRequest;
import com.dontworry.crawler.dto.KeywordCrawlRequest;
import com.dontworry.crawler.service.KeywordCrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        KeywordCallbackRequest.KeywordData response =
                keywordCrawlingService.crawl(request.keyword());
        return ResponseEntity.ok(response);
    }
}