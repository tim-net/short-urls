package com.neueda.shorturls.repository;

import com.neueda.shorturls.domain.ShortURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortURL, Long> {
    ShortURL findByCode(String code);
}
