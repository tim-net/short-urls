package com.neueda.shorturls.api.rest;

import com.neueda.shorturls.api.rest.representation.ShortUrlRepresentation;
import com.neueda.shorturls.service.ShortUrlService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/urls")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PutMapping
    public ShortUrlRepresentation create(String longUrl) {
        String code = shortUrlService.createAndGetCode(longUrl);
        return new ShortUrlRepresentation(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/" + code);
    }

}
