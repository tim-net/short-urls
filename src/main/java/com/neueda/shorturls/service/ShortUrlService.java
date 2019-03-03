package com.neueda.shorturls.service;

import com.neueda.shorturls.domain.ShortURL;
import com.neueda.shorturls.exception.CodeAlreadyExistsException;
import com.neueda.shorturls.repository.ShortUrlRepository;
import com.neueda.shorturls.util.ShortUrlHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for operations with short url objects.
 */
@Service
@Transactional
public class ShortUrlService {
    /**
     * Length of a code generated.
     * The code is a part of a created short url.
     */
    private final static int CODE_LENGTH = 6;
    private final ShortUrlRepository shortUrlRepository;
    private final ShortUrlHelper shortUrlHelper;

    public ShortUrlService(ShortUrlRepository shortUrlRepository, ShortUrlHelper shortUrlHelper) {
        this.shortUrlRepository = shortUrlRepository;
        this.shortUrlHelper = shortUrlHelper;
    }

    /**
     * Tries to create a short url object.
     * Finds an existent short url by generated code and
     * if it exists check if it is the same long url. If long url is
     * the same then returns it otherwise creates a new one.
     *
     * @param code    Code of short url to create.
     * @param longUrl Long url correspondent to the code.
     * @return Created short url object.
     * @throws CodeAlreadyExistsException Thrown if there is already a short url with the same
     *                                    code but different long url. Evades unique constraint violation and resolves conflict between
     *                                    two long url with the same code.
     */
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

    /**
     * Generates a code from a long url and tries to create
     * a short url object.
     *
     * @param longUrl    Long url for which to create a short url.
     * @param startIndex Start index in hashed char sequence of the long url to discern a short code from it.
     * @param endIndex   End index in hashed char sequence of the long url to discern a short code from it.
     * @return Created short url.
     */
    private ShortURL createRecursive(String longUrl, int startIndex, int endIndex) {
        String code = shortUrlHelper.getShortUrl(longUrl, startIndex, endIndex);
        try {
            return tryCreate(code, longUrl);
        } catch (CodeAlreadyExistsException e) {
            return createRecursive(longUrl, endIndex + 1, endIndex + CODE_LENGTH);
        }
    }

    /**
     * Creates a short url from a long one.
     *
     * @param longUrl Long url for which to create a short one.
     * @return Code of creates short url for further short url string construction.
     */
    public String createAndGetCode(String longUrl) {
        return createRecursive(longUrl, 0, CODE_LENGTH).getCode();
    }

    /**
     * Get a short url object by its code.
     *
     * @param code Code to search
     * @return Found short url.
     */
    public ShortURL getByCode(String code) {
        return shortUrlRepository.findByCode(code);
    }


}
