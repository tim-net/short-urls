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

@Controller
@RequestMapping("/")
public class RedirectController {

    private final ShortUrlService shortUrlService;
    private final RedirectStatisticsService redirectStatisticsService;

    public RedirectController(ShortUrlService shortUrlService, RedirectStatisticsService redirectStatisticsService) {
        this.shortUrlService = shortUrlService;
        this.redirectStatisticsService = redirectStatisticsService;
    }

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
