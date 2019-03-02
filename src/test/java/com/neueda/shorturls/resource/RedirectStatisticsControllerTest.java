package com.neueda.shorturls.resource;

import com.neueda.shorturls.api.rest.RedirectStatisticsController;
import com.neueda.shorturls.dto.RedirectStatisticsSummary;
import com.neueda.shorturls.repository.RedirectStatisticsRepository;
import com.neueda.shorturls.service.RedirectStatisticsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RedirectStatisticsController.class)
public class RedirectStatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RedirectStatisticsRepository repository;

    private RedirectStatisticsSummary summary;
    private String code = "1Gdpfjd";
    private String originalUrl = "http://originalUrl";

    @Before
    public void init() {


        LocalDateTime first = LocalDateTime.of(2019, Month.MARCH, 1, 0, 0);
        LocalDateTime last = LocalDateTime.of(2019, Month.MARCH, 2, 0, 0);


        summary = new RedirectStatisticsSummary(code, originalUrl, first, last, 3L);

        doReturn(summary).when(repository).findSummaryByCode(code);
    }

    @Test
    public void getSummary() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/redirect-statistics/summary")
                .param("code", code).accept(
                        MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedResult = String.format("{code:'%s',firstRedirected:'2019-03-01T00:00:00'," +
                "lastRedirected:'2019-03-02T00:00:00',originalUrl:'%s',count:3}", code, originalUrl);
        JSONAssert.assertEquals(expectedResult, result.getResponse()
                .getContentAsString(), false);
    }


    @Configuration
    public static class LocalTestConfiguration {
        @Autowired
        private AutowireCapableBeanFactory factory;

        @Bean
        public RedirectStatisticsService redirectStatisticsService() {
            return factory.createBean(RedirectStatisticsService.class);
        }

        @Bean
        public RedirectStatisticsController redirectStatisticsController() {
            return factory.createBean(RedirectStatisticsController.class);
        }


    }
}
