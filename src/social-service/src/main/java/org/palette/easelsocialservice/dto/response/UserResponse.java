package org.palette.easelsocialservice.dto.response;

import org.palette.easelsocialservice.persistence.domain.User;
import org.palette.easelsocialservice.persistence.relationship.TagsUser;

public record UserResponse(

        Boolean isFollowing,
        Long id,
        String username,
        String nickname,
        String imagePath,
        String status
) {
    public static UserResponse from(TagsUser taggedUser) {
        final User user = taggedUser.getUser();
        return new UserResponse(
                false,
                user.getUid(),
                user.getUsername(),
                user.getNickname(),
                user.getImagePath(),
                user.getActiveString()
        );
    }

    public static UserResponse from(User user) {
        return new UserResponse(
                false,
                user.getUid(),
                user.getUsername(),
                user.getNickname(),
                user.getImagePath(),
                user.getActiveString()
        );
    }
}
