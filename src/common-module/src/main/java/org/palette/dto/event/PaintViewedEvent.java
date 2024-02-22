package org.palette.dto.event;

import org.palette.dto.EaselEvent;

public record PaintViewedEvent(Long paintId) implements EaselEvent {

    @Override
    public String getTopic() {
        return TopicConstant.PAINT_VIEWED.value;
    }
}
