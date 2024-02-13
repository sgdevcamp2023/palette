package org.palette.easelsearchservice.dto.response;

public record HashtagResponse(
        Integer start,
        Integer end,
        String tag
) {
}
