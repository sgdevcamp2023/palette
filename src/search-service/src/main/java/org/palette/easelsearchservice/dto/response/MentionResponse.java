package org.palette.easelsearchservice.dto.response;

import org.palette.grpc.GMentionResponse;

public record MentionResponse(
        Integer start,
        Integer end,
        Long userId,
        String mention
) {
    public static MentionResponse from(GMentionResponse gMention) {
        return new MentionResponse(
                gMention.getStart(),
                gMention.getEnd(),
                gMention.getUserId(),
                gMention.getMention()
        );
    }
}
