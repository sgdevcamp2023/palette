package org.palette.easeltimelineservice.service;

import org.palette.easeltimelineservice.persistence.domain.Paint;

import java.time.LocalDateTime;
import java.util.Optional;

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
                Optional.ofNullable(paint.getQuotePaint()).map(PaintResponse::from).orElse(null),
                paint.getCreatedAt(),
                paint.getText(),
                null,
                null,
                null,
                false,
                false,
                false,
                0,
                new Entities(null, null),
                new Includes(null, null, null)
        );
    }

    public static PaintResponse of(Paint paint, PaintMetrics metrics) {
        return new PaintResponse(
                paint.getId(),
                paint.getIsReply(),
                paint.getAuthorId(),
                paint.getAuthorUsername(),
                paint.getAuthorNickname(),
                paint.getAuthorImagePath(),
                paint.getAuthorStatus(),
                Optional.ofNullable(paint.getQuotePaint()).map(PaintResponse::from).orElse(null),
                paint.getCreatedAt(),
                paint.getText(),
                metrics.replyCount(),
                metrics.repaintCount(),
                metrics.likeCount(),
                false,
                false,
                false,
                0,
                new Entities(null, null),
                new Includes(null, null, null)
        );
    }
}
