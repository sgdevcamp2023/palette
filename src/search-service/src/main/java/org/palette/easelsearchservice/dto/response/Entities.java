package org.palette.easelsearchservice.dto.response;

import java.util.List;


public record Entities(
        List<HashtagResponse> hashtags,
        List<MentionResponse> mentions
) {
}
