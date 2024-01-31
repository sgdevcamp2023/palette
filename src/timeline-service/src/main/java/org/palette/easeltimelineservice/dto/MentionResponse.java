package org.palette.easeltimelineservice.dto;

public record MentionResponse(
        Integer start,
        Integer end,
        Long userId,
        String mention
) {
}
