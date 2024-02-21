package org.palette.easelsearchservice.dto.response;

import org.apache.catalina.User;
import org.palette.grpc.GUserResponse;

public record UserResponse(
        Long id,
        String username,
        String nickname,
        String imagePath,
        String status,
        Boolean isFollowing
) {
    public static UserResponse from(GUserResponse gUser) {
        return new UserResponse(
                gUser.getId(),
                gUser.getUsername(),
                gUser.getNickname(),
                gUser.getImagePath(),
                gUser.getStatus(),
                false
        );
    }
}
