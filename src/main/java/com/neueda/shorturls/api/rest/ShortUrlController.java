package com.neueda.shorturls.api.rest;

import com.neueda.shorturls.api.rest.representation.ShortUrlRepresentation;
import com.neueda.shorturls.service.ShortUrlService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for operating with functionality
 * concerning short urls
 */
@RestController
@RequestMapping("/api/urls")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    /**
     * Creates a short url from a long one.
     *
     * @param longUrl A long URL for which to create a short one.
     * @return representation with created short url
     */
    @PutMapping
    public ShortUrlRepresentation create(String longUrl) {
        String code = shortUrlService.createAndGetCode(longUrl);
        return new ShortUrlRepresentation(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/" + code);
    }

}
