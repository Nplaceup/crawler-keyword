package com.dontworry.crawler.consumer;

import com.dontworry.crawler.config.RabbitMqConfig;
import com.dontworry.crawler.dto.KeywordCallbackRequest;
import com.dontworry.crawler.dto.KeywordRequest;
import com.dontworry.crawler.service.CallbackService;
import com.dontworry.crawler.service.KeywordCrawlingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeywordConsumer {

    private final KeywordCrawlingService crawlingService;
    private final CallbackService callbackService;

    @Transactional
    @RabbitListener(queues = RabbitMqConfig.KEYWORD_QUEUE)
    public void consume(KeywordRequest request) {
        log.info("[KeywordConsumer] 키워드 검색량 조회 시작, 키워드={}", request.keyword());
        KeywordCallbackRequest.KeywordData response = crawlingService.crawl(request.keyword());
        log.info("[KeywordConsumer] 키워드 검색량 response={}", response);

        callbackService.send(request.keyword(), request.crawlDate(), response, request.callbackUrl());

        log.info("[KeywordConsumer] 키워드 검색량 조회 종료, 키워드={}", request.keyword());
    }

}
