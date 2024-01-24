package org.palette.easelauthservice.dto.request;

public record EmailAuthRequest(
        String email,
        String payload
) {
}
