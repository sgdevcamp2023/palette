package org.palette.easelsocialservice.dto.response;

import org.palette.easelsocialservice.persistence.domain.Paint;
import org.palette.easelsocialservice.persistence.domain.PaintMetrics;
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
    public static PaintResponse buildByPaint(Paint paint, PaintResponse quotePaint,
                                             Entities entities, Includes includes, PaintMetrics metrics) {
        User author = paint.getAuthor().getUser();
        return new PaintResponse(
                paint.getPid(),
                paint.getInReplyToPaint() != null,
                author.getUid(),
                author.getUsername(),
                author.getNickname(),
                author.getImagePath(),
                author.getActiveString(),
                quotePaint,
                paint.getCreatedAt(),
                paint.getContent(),
                metrics.getReplyCount(),
                metrics.getRepaintCount(),
                metrics.getLikeCount(),
                metrics.isLike(),
                metrics.isRepainted(),
                metrics.isMarked(),
                paint.getViews(),
                entities,
                includes
        );
    }

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
                null,
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
