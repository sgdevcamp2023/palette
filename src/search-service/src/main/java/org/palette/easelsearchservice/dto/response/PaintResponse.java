package org.palette.easelsearchservice.dto.response;

import java.time.LocalDateTime;

public record PaintResponse(
        Long id,
        Boolean isReply,
        Long authorId,
        String authorUsername,
        String authorNickname,
        String authorImagePath,
        String authorStatus,
        PaintResponse quotePaint,
        LocalDateTime createdAt,
        String text,
        Integer replyCount,
        Integer repaintCount,
        Integer likeCount,
        Boolean like,
        Boolean repainted,
        Boolean marked,
        Integer views,
        Entities entities,
        Includes includes
) {

    // TODO: add real Paint data
    public PaintResponse(Long id, String text) {
        this(id, false, null, null, null, null, null, null, null, text, 0, 0, 0, false, false, false, 0, null, null);
    }
}
