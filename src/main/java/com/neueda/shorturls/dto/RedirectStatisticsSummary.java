package com.neueda.shorturls.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * DTO object for getting summary statistics from DB.
 * Used in the JPA query as a return type.
 */
@AllArgsConstructor
@Getter
public class RedirectStatisticsSummary {

    private String code;

    private String originalUrl;

    private LocalDateTime firstRedirected;

    private LocalDateTime lastRedirected;

    private Long count;

}
