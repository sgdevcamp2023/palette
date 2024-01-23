package org.palette.easelauthservice.dto.request;

public record EmailAuthRequest(
        Long userId,
        String payload
) {
}
