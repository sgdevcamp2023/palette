package org.palette.dto.event;

import org.palette.dto.EaselEvent;

public record PaintViewedEvent(Long paintId) implements EaselEvent {
    public static PaintViewedEvent from(Long paintId) {
        return new PaintViewedEvent(paintId);
    }

    @Override
    public String getTopic() {
        return TopicConstant.PAINT_VIEWED.value();
    }
}
