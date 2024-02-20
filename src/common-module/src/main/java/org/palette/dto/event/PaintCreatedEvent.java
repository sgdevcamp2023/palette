package org.palette.dto.event;

import lombok.AccessLevel;
import lombok.Builder;
import org.palette.dto.EaselEvent;
import org.palette.dto.event.detail.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record PaintCreatedEvent(
        Long id,
        Long inReplyToPaintId,
        Long authorId,
        String authorUsername,
        String authorNickname,
        String authorImagePath,
        String authorStatus,
        PaintCreatedEvent quotePaint,
        LocalDateTime createdAt,
        String text,
        List<HashtagRecord> hashtagRecords,
        List<MentionRecord> mentionRecords,
        List<UserRecord> taggedUserRecords,
        List<MediaRecord> mediaRecords,
        List<LinkRecord> linkRecords

) implements EaselEvent {

    @Override
    public String getTopic() {
        return TopicConstant.PAINT_CREATED.value;
    }
}
