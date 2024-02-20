package org.palette.easelsearchservice.dto.response;

import org.palette.grpc.GPaintResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public static PaintResponse buildByGPaintResponse(GPaintResponse gPaint, PaintResponse quotePaint,
                                             Entities entities, Includes includes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return new PaintResponse(
                gPaint.getPid(),
                gPaint.getIsReply(),
                gPaint.getAuthorId(),
                gPaint.getAuthorUsername(),
                gPaint.getAuthorNickname(),
                gPaint.getAuthorImagePath(),
                gPaint.getAuthorStatus(),
                quotePaint,
                LocalDateTime.parse(gPaint.getCreatedAt(), formatter),
                gPaint.getText(),
                0,
                0,
                0,
                false,
                false,
                false,
                0,
                entities,
                includes
        );
    }

    public static PaintResponse buildByGPaintResponse(GPaintResponse gPaint, Entities entities, Includes includes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return new PaintResponse(
                gPaint.getPid(),
                gPaint.getIsReply(),
                gPaint.getAuthorId(),
                gPaint.getAuthorUsername(),
                gPaint.getAuthorNickname(),
                gPaint.getAuthorImagePath(),
                gPaint.getAuthorStatus(),
                null,
                LocalDateTime.parse(gPaint.getCreatedAt(), formatter),
                gPaint.getText(),
                0,
                0,
                0,
                false,
                false,
                false,
                0,
                entities,
                includes
        );
    }
}
