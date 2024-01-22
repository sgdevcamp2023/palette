package org.palette.easeluserservice.exception;

import lombok.Builder;

@Builder
public record ExceptionResponse(
        String code,
        String message,
        String description
) {
}
