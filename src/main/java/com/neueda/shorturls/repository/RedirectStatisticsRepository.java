package com.neueda.shorturls.repository;

import com.neueda.shorturls.domain.RedirectStatistics;
import com.neueda.shorturls.dto.RedirectStatisticsSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RedirectStatisticsRepository extends JpaRepository<RedirectStatistics, Long> {
    @Query("select new com.neueda.shorturls.dto.RedirectStatisticsSummary(s.url.code, s.url.originalUrl," +
            " min(s.redirectDate), max(s.redirectDate), count(s)) " +
            "from RedirectStatistics s where s.url.code=:code")
    RedirectStatisticsSummary findSummaryByCode(@Param("code") String code);
}
