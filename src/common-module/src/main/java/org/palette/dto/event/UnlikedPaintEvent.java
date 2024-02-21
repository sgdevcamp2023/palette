package org.palette.dto.event;

import org.palette.dto.EaselEvent;

public record UnlikedPaintEvent(
        Long unlikingUserId,
        Long paintId,
        Long likedPaintUserId
) implements EaselEvent {
    @Override
    public String getTopic() {
        return TopicConstant.UNLIKED_PAINT.value;
    }

    public UnlikedPaintEvent(final Long unlikingUserId, final Long paintId) {
        this(unlikingUserId, paintId, -1L);
    }
}
