package com.neueda.shorturls.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.neueda.shorturls.domain.RedirectStatistics;
import com.neueda.shorturls.dto.RedirectStatisticsSummary;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for redirection statistics repository testing.
 */
@DatabaseSetup("/data/repository/db-before.yml")
@DatabaseSetup("/data/repository/redirect-stat/db-before.yml")
@DatabaseTearDown("/data/repository/db-teardown.yml")
public class RedirectStatisticsRepositoryTest extends RepositoryTestBase {
    @Autowired
    private RedirectStatisticsRepository repository;

    @Test
    public void givenStatisticsExists() {
        List<RedirectStatistics> statistics = repository.findAll();
        assertNotNull(statistics);
        assertEquals(3, statistics.size());
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        assertEquals(ids, statistics.stream().mapToLong(RedirectStatistics::getId).boxed()
                .collect(Collectors.toList()));
    }

    @Test
    public void testSummary() {
        String code = "fRie8d";
        RedirectStatisticsSummary summary = repository.findSummaryByCode(code);
        assertNotNull(summary);
        assertEquals(code, summary.getCode());
        assertEquals("http://testOriginalUrl", summary.getOriginalUrl());
        LocalDateTime firstDate = LocalDateTime.of(2019, Month.JANUARY, 1, 0, 0);
        assertEquals(firstDate, summary.getFirstRedirected());
        LocalDateTime lastDate = LocalDateTime.of(2019, Month.JANUARY, 3, 0, 0);
        assertEquals(lastDate, summary.getLastRedirected());
    }
}
