package org.palette.easelsocialservice.dto.request;

import java.util.List;
import java.util.Optional;

public record PaintCreateRequest(
        String text,
        Optional<List<Media>> medias,
        Optional<List<String>> taggedUserIds,
        Optional<String> quotePaintId,
        Optional<String> inReplyToPostId,
        Optional<List<Hashtag>> hashtags,
        Optional<List<Mention>> mentions,
        Optional<List<Link>> links) {

    public record Media (
            String id,
            String type) {}

    public record Hashtag (
            int start,
            int end,
            String tag) {}

    public record Mention (
            int start,
            int end,
            String userId,
            String mention) {}

    public record Link (
            int start,
            int end,
            String url) {}
}
