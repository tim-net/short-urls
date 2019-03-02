package com.neueda.shorturls.resource;

import com.neueda.shorturls.RedirectController;
import com.neueda.shorturls.api.rest.ShortUrlController;
import com.neueda.shorturls.domain.ShortURL;
import com.neueda.shorturls.repository.RedirectStatisticsRepository;
import com.neueda.shorturls.repository.ShortUrlRepository;
import com.neueda.shorturls.service.RedirectStatisticsService;
import com.neueda.shorturls.service.ShortUrlService;
import com.neueda.shorturls.util.ShortUrlHelper;
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

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ShortUrlController.class)
public class ShortUrlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShortUrlRepository repository;

    @MockBean
    private RedirectStatisticsRepository redirectStatisticsRepository;

    private ShortURL shortURL;
    private String code;
    private String originalUrl;


    @Before
    public void init() {

        code = "1Gdpfjd";
        originalUrl = "http://originalUrl";
        shortURL = new ShortURL(code, originalUrl);

        LocalDateTime created = LocalDateTime.of(2019, Month.MARCH, 1, 0, 0);
        LocalDateTime updated = LocalDateTime.of(2019, Month.MARCH, 2, 0, 0);


        // As created field cannot be inserted or updated, we need to set it explicitly using reflection
        try {
            Field createdField = ShortURL.class.getDeclaredField("created");
            createdField.setAccessible(true);
            createdField.set(shortURL, created);

            Field updatedField = ShortURL.class.getDeclaredField("updated");
            updatedField.setAccessible(true);
            updatedField.set(shortURL, updated);


        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("There is a mistake in the test code");
        }
        doReturn(shortURL).when(repository).findByCode(code);
    }

    @Test
    public void testRedirect() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/" + code);
        mockMvc.perform(requestBuilder).andExpect(redirectedUrl(originalUrl));
    }

    @Test
    public void test_createShortUrl() throws Exception {
        ShortURL expected = new ShortURL("I3EZQx", "http://longUrlTest");
        doReturn(expected).when(repository).save(expected);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
                "/api/urls")
                .param("longUrl", "http://longUrlTest").accept(
                        MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedShortUrl = "http://" + InetAddress.getLoopbackAddress().getHostName() + "/" + "I3EZQx";
        String expectedResult = String.format("{'shortUrl':'%s'}", expectedShortUrl);
        JSONAssert.assertEquals(expectedResult, result.getResponse()
                .getContentAsString(), false);
    }


    @Configuration
    public static class LocalTestConfiguration {
        @Autowired
        private AutowireCapableBeanFactory factory;

        @Bean
        public ShortUrlHelper shortUrlHelper() {
            return factory.createBean(ShortUrlHelper.class);
        }

        @Bean
        public ShortUrlService shortUrlService() {
            return factory.createBean(ShortUrlService.class);
        }

        @Bean
        public ShortUrlController shortUrlController() {
            return factory.createBean(ShortUrlController.class);
        }

        @Bean
        public RedirectController redirectController() {
            return factory.createBean(RedirectController.class);
        }

        @Bean
        public RedirectStatisticsService redirectStatisticsService() {
            return factory.createBean(RedirectStatisticsService.class);
        }
    }
}
