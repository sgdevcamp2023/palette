package org.palette.easelsocialservice.dto.response;

import org.palette.easelsocialservice.persistence.relationship.Tags;

public record HashtagResponse(
        Integer start,
        Integer end,
        String tag
) {

    public static HashtagResponse from(Tags tag) {
        return new HashtagResponse(
                tag.getStart(),
                tag.getEnd(),
                tag.getHashtag().getTag()
        );
    }
}
