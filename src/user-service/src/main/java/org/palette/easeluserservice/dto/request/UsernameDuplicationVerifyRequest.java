package org.palette.easeluserservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UsernameDuplicationVerifyRequest(
        @NotBlank
        String username
) {
}
