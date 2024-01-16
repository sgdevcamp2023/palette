package org.palette.easeluserservice.api.dto.request;

import java.util.Optional;

public record JoinRequest(
        String email,
        String password,
        String username, // @DoHyeon
        String nickname, // DoHyeon
        Optional<String> introduce,
        Optional<String> profilePath,
        Optional<String> backgroundPath,
        Optional<String> websitePath
) {
}
