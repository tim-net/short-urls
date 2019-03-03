package com.neueda.shorturls.util;

import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;

/**
 * Helper class for generation of a code for short url creation.
 */
@Component
public class ShortUrlHelper {
    /**
     * Generates a code which is a part of a short url.
     * Uses MD5 hash algorithm in base 64 format with url safe characters.
     *
     * @param longUrl    Long url from which to create a code.
     * @param startIndex Start index in hashed char sequence of the long url to discern a short code from it.
     * @param endIndex   End index in hashed char sequence of the long url to discern a short code from it.
     * @return Created code.
     */
    public String getShortUrl(String longUrl, int startIndex, int endIndex) {
        String encoded = Base64Utils.encodeToUrlSafeString(DigestUtils.md5Digest(longUrl.getBytes()));
        return encoded.substring(startIndex, endIndex);
    }
}
