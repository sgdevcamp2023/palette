package org.palette.easelsearchservice.dto.response;

public record LinkResponse(
        Integer start,
        Integer end,
        String shortLink,
        String originalLink
) {
}
