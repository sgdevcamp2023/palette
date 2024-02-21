package org.palette.easelauthservice.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
