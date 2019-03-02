package com.neueda.shorturls.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class RedirectStatisticsSummary {

    private String code;

    private String originalUrl;

    private LocalDateTime firstRedirected;

    private LocalDateTime lastRedirected;

    private Long count;

}
