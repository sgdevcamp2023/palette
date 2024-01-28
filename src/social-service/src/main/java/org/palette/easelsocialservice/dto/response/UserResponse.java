package org.palette.easelsocialservice.dto.response;

import org.palette.easelsocialservice.persistence.domain.User;
import org.palette.easelsocialservice.persistence.relationship.TagsUser;

public record UserResponse(
        Long id,
        String username,
        String nickname
) {
    public static UserResponse from(TagsUser taggedUser) {
        final User user = taggedUser.getUser();
        return new UserResponse(
                user.getUid(),
                user.getUsername(),
                user.getNickname()
        );
    }
}
