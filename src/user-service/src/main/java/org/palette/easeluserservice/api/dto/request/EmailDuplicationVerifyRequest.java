package org.palette.easeluserservice.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EmailDuplicationVerifyRequest(
        @NotBlank
        String email
) {
}
