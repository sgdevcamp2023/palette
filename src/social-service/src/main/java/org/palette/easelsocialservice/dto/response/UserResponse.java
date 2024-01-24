package org.palette.easelsocialservice.dto.response;

public record UserResponse(
        String createdAt,
        String id,
        String nickname,
        String username
) {
}
