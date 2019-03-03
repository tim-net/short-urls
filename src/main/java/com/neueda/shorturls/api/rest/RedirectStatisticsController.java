package com.neueda.shorturls.api.rest;

import com.neueda.shorturls.api.rest.representation.RedirectStatisticsRepresentation;
import com.neueda.shorturls.dto.RedirectStatisticsSummary;
import com.neueda.shorturls.exception.CodeNotFoundException;
import com.neueda.shorturls.service.RedirectStatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST controller for getting redirection statistics.
 */
@RestController
@RequestMapping("/api/redirect-statistics")
public class RedirectStatisticsController {

    private final RedirectStatisticsService redirectStatisticsService;

    public RedirectStatisticsController(RedirectStatisticsService redirectStatisticsService) {
        this.redirectStatisticsService = redirectStatisticsService;
    }

    /**
     * Returns representation object including summary statistics
     * of redirects
     *
     * @param code A short code  - path of the short url
     * @return statistics
     */
    @GetMapping("/summary")
    public RedirectStatisticsRepresentation getStatistics(String code) {
        RedirectStatisticsSummary summary = redirectStatisticsService.findSummaryByCode(code);
        if(summary == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Code is not found "+ code, new CodeNotFoundException(code));
        }
        return RedirectStatisticsRepresentation.builder()
                .code(summary.getCode())
                .count(summary.getCount())
                .firstRedirected(summary.getFirstRedirected())
                .lastRedirected(summary.getLastRedirected())
                .originalUrl(summary.getOriginalUrl())
                .build();
    }

}
