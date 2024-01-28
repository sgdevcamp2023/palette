package org.palette.easelsocialservice.dto.response;

import org.palette.easelsocialservice.persistence.domain.Paint;
import org.palette.easelsocialservice.persistence.domain.User;

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
        User author = paint.getAuthor().getUser();
        return new PaintResponse(
                paint.getPid(),
                paint.getInReplyToPaint() != null,
                author.getUid(),
                author.getUsername(),
                author.getNickname(),
                author.getImagePath(),
                author.getActiveString(),
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
