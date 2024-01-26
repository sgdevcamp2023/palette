package org.palette.easelsocialservice.dto.response;

public record LinkResponse(
        Integer start,
        Integer end,
        String shortUrl,
        String originalUrl
) {
}
