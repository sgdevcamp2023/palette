package org.palette.easeluserservice.dto.request;

public record EditProfileRequest(
        String nickname,
        String introduce,
        String profileImagePath,
        String backgroundImagePath,
        String websitePath
) {
}
