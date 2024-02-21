package org.palette.dto.event;

import org.palette.dto.EaselEvent;

public record RepaintCreatedEvent(
        Long repaintUserId,
        Long repaintedPaintId,
        Long originalPaintUserId
) implements EaselEvent {
    @Override
    public String getTopic() {
        return TopicConstant.REPAINT_CREATED.value;
    }
}
