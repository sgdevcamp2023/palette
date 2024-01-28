package org.palette.easelsocialservice.dto.response;

import java.util.List;

public record Includes(
        List<MediaResponse> medias,
        List<UserResponse> users,
        List<LinkResponse> links
) {
}
