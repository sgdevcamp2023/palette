package org.palette.easelsocialservice.dto.response;

import org.palette.easelsocialservice.persistence.relationship.Mentions;

public record MentionResponse(
        Integer start,
        Integer end,
        Long userId,
        String mention
) {
    public static MentionResponse from(Mentions mention) {
        return new MentionResponse(
                mention.getStart(),
                mention.getEnd(),
                mention.getUser().getUid(),
                mention.getUser().getUsername()
        );
    }
}
