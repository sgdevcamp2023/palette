package org.palette.easelsocialservice.dto.response;

import org.palette.easelsocialservice.persistence.domain.Paint;

import java.time.LocalDateTime;

public record PaintResponse(
        Long id,
        Boolean isReply,
        Long authorId,
        String authorUsername,
        String authorNickname,
        String authorImagePath,
        String authorStatus,
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
    public static PaintResponse buildByPaint(Paint paint, Entities entities, Includes includes) {
        return new PaintResponse(
                paint.getPid(),
                paint.getInReplyToPaint() != null,
                paint.getAuthor().getUser().getUid(),
                paint.getAuthor().getUser().getUsername(),
                paint.getAuthor().getUser().getNickname(),
                paint.getAuthor().getUser().getImagePath(),
                paint.getAuthor().getUser().getIsActive() ? "public" : "private",
                paint.getCreatedAt(),
                paint.getContent(),
                0,
                paint.getRepaints().size(),
                0,
                false,
                false,
                false,
                paint.getViews(),
                entities,
                includes
        );
    }
}
