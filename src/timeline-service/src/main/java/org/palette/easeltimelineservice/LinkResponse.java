package org.palette.easeltimelineservice;

public record LinkResponse(
        Integer start,
        Integer end,
        String shortLink,
        String originalLink
) {
}
