package org.palette.easeluserservice.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UsernameVerifyRequest(
        @NotBlank
        String username
) {
}
