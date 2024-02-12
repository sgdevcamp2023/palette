package org.palette.easelsearchservice.dto.response;

public record UserResponse(

        Boolean isFollowing,
        Long id,
        String username,
        String nickname,
        String imagePath,
        String status
) {
}
