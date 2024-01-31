package org.palette.easeltimelineservice.dto;


public record PaintCreatedEvent(
        Long userId,
        Long paintId,
        PaintResponse paintResponse
) {
}
