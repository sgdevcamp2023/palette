package org.palette.easeluserservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record TemporaryJoinRequest(
        @Email
        String email,
        @NotBlank
        String nickname
) {
}
