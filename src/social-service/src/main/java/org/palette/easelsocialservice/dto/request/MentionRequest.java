package org.palette.easelsocialservice.dto.request;

public record MentionRequest(
        int start,
        int end,
        Long userId,
        String mention) {
}
