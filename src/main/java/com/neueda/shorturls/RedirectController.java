package com.neueda.shorturls;

import com.neueda.shorturls.domain.ShortURL;
import com.neueda.shorturls.exception.CodeNotFoundException;
import com.neueda.shorturls.service.RedirectStatisticsService;
import com.neueda.shorturls.service.ShortUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to process HTTP requests of short urls.
 */
@Controller
@RequestMapping("/")
public class RedirectController {

    private final ShortUrlService shortUrlService;
    private final RedirectStatisticsService redirectStatisticsService;

    public RedirectController(ShortUrlService shortUrlService, RedirectStatisticsService redirectStatisticsService) {
        this.shortUrlService = shortUrlService;
        this.redirectStatisticsService = redirectStatisticsService;
    }

    /**
     * Gets the long url by its code (which is a path)
     * and redirects to the correspondent long url.
     *
     * @param code Code by which to do the redirection.
     * @return Object used in spring framework to resolve to the redirection.
     */
    @GetMapping("{code}")
    public ModelAndView redirect(@PathVariable String code) {
        ShortURL url = shortUrlService.getByCode(code);
        if (url == null) {
            throw new CodeNotFoundException(code);
        }
        redirectStatisticsService.create(url);
        return new ModelAndView("redirect:" + url.getOriginalUrl());
    }
}
