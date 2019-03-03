package com.neueda.shorturls.api.rest.representation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * REST representation class for creation of short url.
 */
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ApiModel(value = "ShortUrlRepresentation", description = "Result of short url creation")
public class ShortUrlRepresentation {
    @ApiModelProperty(value = "ShortUrl", position = 10)
    private String shortUrl;
}
