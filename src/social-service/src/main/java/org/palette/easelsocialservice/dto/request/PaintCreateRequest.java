package org.palette.easelsocialservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

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
