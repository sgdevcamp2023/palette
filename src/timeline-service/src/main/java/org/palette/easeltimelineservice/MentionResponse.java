package org.palette.easeltimelineservice;

public record MentionResponse(
        Integer start,
        Integer end,
        Long userId,
        String mention
) {
}
