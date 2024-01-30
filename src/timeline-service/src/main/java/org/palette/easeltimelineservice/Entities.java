package org.palette.easeltimelineservice;

import java.util.List;


public record Entities(
        List<HashtagResponse> hashtags,
        List<MentionResponse> mentions
) {
}
