package org.palette.exception;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PROTECTED)
public record ExceptionResponse(
        String code,
        String message,
        String description
) {
}
