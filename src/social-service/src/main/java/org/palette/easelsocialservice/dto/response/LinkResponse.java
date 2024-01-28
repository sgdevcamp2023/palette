package org.palette.easelsocialservice.dto.response;

public record LinkResponse(
        Integer start,
        Integer end,
        String shortLink,
        String originalLink
) {
}
