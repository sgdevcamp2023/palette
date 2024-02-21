package org.palette.easelsocialservice.dto.response;

import org.palette.easelsocialservice.persistence.relationship.Contains;

public record LinkResponse(
        Integer start,
        Integer end,
        String shortLink,
        String originalLink
) {
    public static LinkResponse from(Contains link) {
        return new LinkResponse(
                link.getStart(),
                link.getEnd(),
                link.getLink().getShortLink(),
                link.getLink().getOriginalLink()
        );
    }
}
