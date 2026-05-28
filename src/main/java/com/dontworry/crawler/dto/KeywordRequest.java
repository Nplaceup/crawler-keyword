package com.dontworry.crawler.dto;

import java.time.LocalDate;

public record KeywordRequest(
        String keyword,
        LocalDate crawlDate,
        String callbackUrl   // null 이면 Response 직접 반환
) {}