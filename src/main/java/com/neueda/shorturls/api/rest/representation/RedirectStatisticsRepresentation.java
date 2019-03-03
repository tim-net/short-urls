package com.neueda.shorturls.api.rest.representation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * REST representation class for
 * statistics of redirects
 */
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ApiModel(value = "RedirectStatisticsRepresentation", description = "Redirect statistics about an url")
public class RedirectStatisticsRepresentation {

    @ApiModelProperty(value = "code", position = 10)
    private String code;

    @ApiModelProperty(value = "OriginalUrl", position = 20)
    private String originalUrl;

    @ApiModelProperty(value = "firstRedirected", example = "2019-01-01 00:00:00", position = 30)
    private LocalDateTime firstRedirected;

    @ApiModelProperty(value = "lastRedirected", example = "2019-01-01 00:00:00", position = 40)
    private LocalDateTime lastRedirected;

    @ApiModelProperty(value = "count", example = "1", position = 50)
    private Long count;

}
