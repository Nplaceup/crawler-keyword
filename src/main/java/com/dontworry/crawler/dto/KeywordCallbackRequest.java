package com.dontworry.crawler.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.List;

public record KeywordCallbackRequest(
        String keyword,
        LocalDate crawlDate,
        KeywordData data
) {
    public record KeywordData(
            List<RelKwdStat> keywordList
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record RelKwdStat(
            String relKeyword,
            String monthlyPcQcCnt,
            String monthlyMobileQcCnt,
            String compIdx
    ) {}
}
