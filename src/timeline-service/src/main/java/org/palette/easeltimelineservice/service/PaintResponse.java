package org.palette.easeltimelineservice.service;

import org.palette.easeltimelineservice.persistence.domain.Paint;

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
    public static PaintResponse from(Paint paint) {
        return new PaintResponse(
                paint.getId(),
                paint.getIsReply(),
                paint.getAuthorId(),
                paint.getAuthorUsername(),
                paint.getAuthorNickname(),
                paint.getAuthorImagePath(),
                paint.getAuthorStatus(),
                paint.getQuotePaint() == null ? null : PaintResponse.from(paint.getQuotePaint()),
                paint.getCreatedAt(),
                paint.getText(),
                0,
                0,
                0,
                false,
                false,
                false,
                0,
                new Entities(null, null),
                new Includes(null, null, null)
        );
    }
}
