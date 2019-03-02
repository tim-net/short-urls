package com.neueda.shorturls.api.rest;

import com.neueda.shorturls.api.rest.representation.RedirectStatisticsRepresentation;
import com.neueda.shorturls.dto.RedirectStatisticsSummary;
import com.neueda.shorturls.service.RedirectStatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/redirect-statistics")
public class RedirectStatisticsController {

    private final RedirectStatisticsService redirectStatisticsService;

    public RedirectStatisticsController(RedirectStatisticsService redirectStatisticsService) {
        this.redirectStatisticsService = redirectStatisticsService;
    }

    @GetMapping("/summary")
    public RedirectStatisticsRepresentation getStatistics(String code) {
        RedirectStatisticsSummary summary = redirectStatisticsService.findSummaryByCode(code);
        return RedirectStatisticsRepresentation.builder()
                .code(summary.getCode())
                .count(summary.getCount())
                .firstRedirected(summary.getFirstRedirected())
                .lastRedirected(summary.getLastRedirected())
                .originalUrl(summary.getOriginalUrl())
                .build();
    }

}
