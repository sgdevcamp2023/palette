package org.palette.easeluserservice.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public record JoinRequest(
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        String username,
        Optional<String> introduce,
        Optional<String> profileImagePath,
        Optional<String> websitePath
) {
}
