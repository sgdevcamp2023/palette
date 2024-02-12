package org.palette.dto.event.detail;

public record LinkRecord(
        Integer start,
        Integer end,
        String shortLink,
        String originalLink
) {
}
