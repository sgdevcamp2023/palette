package org.palette.easelsearchservice.dto.response;

public record UserResponse(
        Long id,
        String username,
        String nickname,
        String imagePath,
        String status,
        Boolean isFollowing
) {
}
