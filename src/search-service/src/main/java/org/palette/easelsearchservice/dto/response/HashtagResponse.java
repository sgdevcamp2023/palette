package org.palette.easelsearchservice.dto.response;

import org.palette.grpc.GHashtagResponse;

public record HashtagResponse(
        Integer start,
        Integer end,
        String tag
) {
    public static HashtagResponse from(GHashtagResponse gHashtagResponse) {
        return new HashtagResponse(
                gHashtagResponse.getStart(),
                gHashtagResponse.getEnd(),
                gHashtagResponse.getTag()
        );
    }
}
