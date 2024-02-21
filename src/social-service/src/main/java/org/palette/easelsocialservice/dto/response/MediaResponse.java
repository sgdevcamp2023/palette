package org.palette.easelsocialservice.dto.response;

import org.palette.easelsocialservice.persistence.relationship.Uses;

public record MediaResponse(
        String type,
        String path
) {
    public static MediaResponse from(Uses media) {
        return new MediaResponse(
                media.getMedia().getType(),
                media.getMedia().getPath()
        );
    }
}
