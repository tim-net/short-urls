package com.neueda.shorturls.service;

import com.neueda.shorturls.domain.RedirectStatistics;
import com.neueda.shorturls.domain.ShortURL;
import com.neueda.shorturls.dto.RedirectStatisticsSummary;
import com.neueda.shorturls.repository.RedirectStatisticsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RedirectStatisticsService {
    private final RedirectStatisticsRepository repository;

    public RedirectStatisticsService(RedirectStatisticsRepository repository) {
        this.repository = repository;
    }

    public RedirectStatisticsSummary findSummaryByCode(String code) {
        return repository.findSummaryByCode(code);
    }

    public void create(ShortURL url) {
        repository.save(new RedirectStatistics(url));
    }

}
