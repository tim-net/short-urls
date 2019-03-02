package com.neueda.shorturls.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.neueda.shorturls.domain.ShortURL;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DatabaseSetup("/data/repository/db-before.yml")
@DatabaseSetup("/data/repository/urls/db-before.yml")
@DatabaseTearDown("/data/repository/db-teardown.yml")
public class ShortUrlRepositoryTest extends RepositoryTestBase  {
    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Test
    public void givenShortUrlExists(){
        Long id =1L;
        List<ShortURL> shortURLS = shortUrlRepository.findAll();
        assertNotNull(shortURLS);
        assertEquals(1, shortURLS.size());
        ShortURL shortURL = shortURLS.get(0);
        assertEquals(id, shortURL.getId());
        assertEquals("fRie8d", shortURL.getCode());
        assertEquals("http://testOriginalUrl", shortURL.getOriginalUrl());
        LocalDateTime created = LocalDateTime.of(2019, Month.JANUARY, 1, 0, 0);
        assertEquals(created, shortURL.getCreated());
        LocalDateTime updated = LocalDateTime.of(2019, Month.FEBRUARY, 1, 0, 0);
        assertEquals(updated, shortURL.getUpdated());
    }
}
