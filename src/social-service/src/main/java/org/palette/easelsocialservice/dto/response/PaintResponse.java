package org.palette.easelsocialservice.dto.response;

public record PaintResponse(
        Long id,
        Boolean isReply,
        String authorId,
        String authorUsername,
        String authorNickname,
        String authorImagePath,
        String authorStatus,
        String createdAt,
        String text,
        Integer replyCount,
        Integer repaintCount,
        Integer likeCount,
        Boolean like,
        Boolean repainted,
        Boolean marked,
        String views,
        Entities entities,
        Includes includes
) {
}
