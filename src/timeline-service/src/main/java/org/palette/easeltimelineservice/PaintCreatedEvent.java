package org.palette.easeltimelineservice;


public record PaintCreatedEvent(
        Long userId,
        Long paintId,
        Paint paint
) {
}
