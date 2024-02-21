package org.palette.passport.component;

public record UserInfo(
        Long id,
        String email,
        String nickname,
        String username,
        String role,
        Boolean isActivated,
        String accessedAt,
        String createdAt,
        String deletedAt
) {
}
