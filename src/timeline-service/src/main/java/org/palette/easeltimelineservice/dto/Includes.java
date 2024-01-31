package org.palette.easeltimelineservice.dto;

import java.util.List;

public record Includes(
        List<MediaResponse> medias,
        List<UserResponse> users,
        List<LinkResponse> links
) {
}
