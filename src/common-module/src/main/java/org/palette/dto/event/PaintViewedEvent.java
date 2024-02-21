package org.palette.dto.event;

public record PaintViewedEvent(Long paintId) {
    public static PaintViewedEvent from(Long paintId) {
        return new PaintViewedEvent(paintId);
    }
}
