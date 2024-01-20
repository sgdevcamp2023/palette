package org.palette.easelsocialservice.dto.request;

import java.util.List;
import java.util.Optional;

public record PaintCreateRequest(
        String text,
        Optional<List<Media>> medias,
        Optional<List<Long>> taggedUserIds,
        Optional<Long> quotePaintId,
        Optional<Long> inReplyToPaintId,
        Optional<List<Hashtag>> hashtags,
        Optional<List<Mention>> mentions,
        Optional<List<Link>> links) {

    public record Media (
            String path,
            String type) {}

    public record Hashtag (
            int start,
            int end,
            String tag) {}

    public record Mention (
            int start,
            int end,
            Long userId,
            String mention) {}

    public record Link (
            int start,
            int end,
            String url) {}
}
