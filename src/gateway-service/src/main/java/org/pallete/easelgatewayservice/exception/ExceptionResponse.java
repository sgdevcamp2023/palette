package org.pallete.easelgatewayservice.exception;

import lombok.Builder;

@Builder
public record ExceptionResponse(
        String code,
        String message,
        String description
) {
}
