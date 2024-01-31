package org.palette.easeltimelineservice.dto;

public record LinkResponse(
        Integer start,
        Integer end,
        String shortLink,
        String originalLink
) {
}
