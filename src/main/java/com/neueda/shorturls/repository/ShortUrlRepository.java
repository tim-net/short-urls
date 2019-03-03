package com.neueda.shorturls.repository;

import com.neueda.shorturls.domain.ShortURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for short url entities.
 */
@Repository
public interface ShortUrlRepository extends JpaRepository<ShortURL, Long> {
    /**
     * Returns a short url by its code.
     *
     * @param code A code to search.
     * @return Entity of the found short url.
     */
    ShortURL findByCode(String code);
}
