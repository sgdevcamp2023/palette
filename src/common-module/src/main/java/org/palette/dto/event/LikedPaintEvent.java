package org.palette.dto.event;

import org.palette.dto.EaselEvent;

public record LikedPaintEvent(
        Long likingUserId,
        Long paintId,
        Long likedPaintUserId
) implements EaselEvent {
    @Override
    public String getTopic() {
        return TopicConstant.LIKED_PAINT.value;
    }
}
