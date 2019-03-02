package com.neueda.shorturls.service;

import com.neueda.shorturls.domain.ShortURL;
import com.neueda.shorturls.exception.CodeAlreadyExistsException;
import com.neueda.shorturls.repository.ShortUrlRepository;
import com.neueda.shorturls.util.ShortUrlHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShortUrlService {
    private final static int CODE_LENGTH = 6;
    private final ShortUrlRepository shortUrlRepository;
    private final ShortUrlHelper shortUrlHelper;

    public ShortUrlService(ShortUrlRepository shortUrlRepository, ShortUrlHelper shortUrlHelper) {
        this.shortUrlRepository = shortUrlRepository;
        this.shortUrlHelper = shortUrlHelper;
    }


    private ShortURL tryCreate(String code, String longUrl) throws CodeAlreadyExistsException {
        ShortURL existent = shortUrlRepository.findByCode(code);
        if (existent != null) {
            if (existent.getOriginalUrl().equals(longUrl)) {
                return existent;
            } else {
                throw new CodeAlreadyExistsException(code);
            }
        }

        return shortUrlRepository.save(new ShortURL(code, longUrl));

    }

    private ShortURL createRecursive(String longUrl, int startIndex, int endIndex) {
        String code = shortUrlHelper.getShortUrl(longUrl, startIndex, endIndex);
        try {
            return tryCreate(code, longUrl);
        } catch (CodeAlreadyExistsException e) {
            return createRecursive(longUrl, endIndex + 1, endIndex + CODE_LENGTH);
        }
    }

    public String createAndGetCode(String longUrl) {
        return createRecursive(longUrl, 0, CODE_LENGTH).getCode();
    }

    public ShortURL getByCode(String code) {
        return shortUrlRepository.findByCode(code);
    }


}
