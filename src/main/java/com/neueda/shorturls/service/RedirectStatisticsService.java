package com.neueda.shorturls.service;

import com.neueda.shorturls.domain.RedirectStatistics;
import com.neueda.shorturls.domain.ShortURL;
import com.neueda.shorturls.dto.RedirectStatisticsSummary;
import com.neueda.shorturls.repository.RedirectStatisticsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for operations with redirection statistic
 * objects.
 */
@Service
@Transactional
public class RedirectStatisticsService {
    private final RedirectStatisticsRepository repository;

    public RedirectStatisticsService(RedirectStatisticsRepository repository) {
        this.repository = repository;
    }

    /**
     * Finds summary of redirection statistics.
     *
     * @param code Code to search
     * @return DTO object of redirection statistics.
     */
    public RedirectStatisticsSummary findSummaryByCode(String code) {
        return repository.findSummaryByCode(code);
    }

    /**
     * Creates an event of a redirection.
     *
     * @param url Short url for which statistics is created.
     */
    public void create(ShortURL url) {
        repository.save(new RedirectStatistics(url));
    }

}
