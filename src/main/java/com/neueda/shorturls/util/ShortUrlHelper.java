package com.neueda.shorturls.util;

import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;

@Component
public class ShortUrlHelper {



    public String getShortUrl(String longUrl, int startIndex, int endIndex) {
        String encoded = Base64Utils.encodeToUrlSafeString(DigestUtils.md5Digest(longUrl.getBytes()));
        return encoded.substring(startIndex, endIndex);
    }
}
