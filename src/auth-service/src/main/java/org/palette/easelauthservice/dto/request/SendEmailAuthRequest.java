package org.palette.easelauthservice.dto.request;

public record SendEmailAuthRequest(
        String email,
        String payload
) {
}
