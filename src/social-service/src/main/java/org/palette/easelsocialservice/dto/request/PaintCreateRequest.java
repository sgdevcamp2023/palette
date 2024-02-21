package org.palette.easelsocialservice.dto.request;

import java.util.List;

public record PaintCreateRequest(
        String text,
        List<MediaRequest> medias,
        List<Long> taggedUserIds,
        Long quotePaintId,
        Long inReplyToPaintId,
        List<HashtagRequest> hashtags,
        List<MentionRequest> mentions,
        List<LinkRequest> links) {
}
