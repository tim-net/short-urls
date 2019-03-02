package com.neueda.shorturls.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "redirect_stat")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
public class RedirectStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id")
    private ShortURL url;

    @Column(name = "redirect_date", nullable = false, updatable = false, insertable = false)
    private LocalDateTime redirectDate;

    public RedirectStatistics(@NotNull ShortURL url) {
        this.url = url;
    }
}
