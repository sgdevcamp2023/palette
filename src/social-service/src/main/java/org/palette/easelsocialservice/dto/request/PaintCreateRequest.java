package org.palette.easelsocialservice.dto.request;

import java.util.List;
import java.util.Optional;

public record PaintCreateRequest(
        String text, Optional<List<MediaRequest>> medias,
        Optional<List<Long>> taggedUserIds,
        Optional<Long> quotePaintId,
        Optional<Long> inReplyToPaintId,
        Optional<List<HashtagRequest>> hashtags,
        Optional<List<MentionRequest>> mentions,
        Optional<List<LinkRequest>> links) {

}
