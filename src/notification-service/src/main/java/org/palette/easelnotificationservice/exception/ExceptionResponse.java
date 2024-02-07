package org.palette.easelnotificationservice.exception;

import lombok.Builder;

@Builder
public record ExceptionResponse(
        String code,
        String message,
        String description
) {
}
