package org.palette.dto.event.detail;

public record HashtagRecord(
        Integer start,
        Integer end,
        String tag
) {
}
