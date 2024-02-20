package org.palette.easelsearchservice.dto.response;

import org.palette.grpc.GLinkResponse;

public record LinkResponse(
        Integer start,
        Integer end,
        String shortLink,
        String originalLink
) {
    public static LinkResponse from(GLinkResponse gLink) {
        return new LinkResponse(
                gLink.getStart(),
                gLink.getEnd(),
                gLink.getShortLink(),
                gLink.getOriginalLink()
        );
    }
}
