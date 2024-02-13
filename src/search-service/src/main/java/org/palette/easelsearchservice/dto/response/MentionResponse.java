package org.palette.easelsearchservice.dto.response;

public record MentionResponse(
        Integer start,
        Integer end,
        Long userId,
        String mention
) {
}
