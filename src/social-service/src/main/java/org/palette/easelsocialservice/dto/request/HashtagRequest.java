package org.palette.easelsocialservice.dto.request;

public record HashtagRequest(
        int start,
        int end,
        String tag) {
}
