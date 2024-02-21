package org.palette.easelsearchservice.dto.response;

import java.util.List;

public record Includes(
        List<MediaResponse> medias,
        List<UserResponse> users,
        List<LinkResponse> links
) {
}
