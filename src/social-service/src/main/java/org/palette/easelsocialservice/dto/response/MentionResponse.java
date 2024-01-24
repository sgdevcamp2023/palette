package org.palette.easelsocialservice.dto.response;

public record MentionResponse(
        Integer start,
        Integer end,
        String userId,
        String mention
) {
}
