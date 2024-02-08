package org.palette.dto.event;

import org.palette.dto.EaselEvent;

public record QuotedPaintEvent(
        Long quotingUserId,
        Long quotedPaintId,
        Long quotedUserId
) implements EaselEvent {
    @Override
    public String getTopic() {
        return TopicConstant.QUOTED_PAINT.value;
    }
}
