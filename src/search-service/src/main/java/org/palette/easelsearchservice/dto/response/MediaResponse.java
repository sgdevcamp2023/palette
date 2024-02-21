package org.palette.easelsearchservice.dto.response;

import org.palette.grpc.GMediaResponse;
import org.palette.grpc.GUserResponse;

public record MediaResponse(
        String type,
        String path
) {
    public static MediaResponse from(GMediaResponse gMedia) {
        return new MediaResponse(
                gMedia.getType(),
                gMedia.getPath()
        );
    }
}
