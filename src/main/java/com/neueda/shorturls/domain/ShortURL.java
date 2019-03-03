package com.neueda.shorturls.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DB entity for storing created short urls.
 */
@Entity
@Table(name = "urls")
@Getter
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@EqualsAndHashCode(exclude = {"id", "created", "updated"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortURL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 6)
    @Column(name = "code", unique = true)
    private String code;

    @NotNull
    @Size(max = 256)
    @Column(name = "original_url")
    private String originalUrl;

    @Column(name = "created", nullable = false, updatable = false, insertable = false)
    private LocalDateTime created;

    @Column(name = "updated", nullable = false, updatable = false, insertable = false)
    private LocalDateTime updated;

    @OneToMany(mappedBy = "url", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RedirectStatistics> redirectStatistics;

    public ShortURL(String code, String originalUrl) {
        this.code = code;
        this.originalUrl = originalUrl;
    }

}
