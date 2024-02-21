package org.palette.easeltimelineservice.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.palette.dto.event.PaintCreatedEvent;
import org.palette.dto.event.detail.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Paint {

    private Long id;
    private Boolean isReply = false;
    private Long authorId;
    private String authorUsername;
    private String authorNickname;
    private String authorImagePath;
    private String authorStatus;
    private Paint quotePaint = null;
    private LocalDateTime createdAt;
    private String text;
    private List<HashtagRecord> hashtagRecords;
    private List<MentionRecord> mentionRecords;
    private List<UserRecord> taggedUserRecords;
    private List<MediaRecord> mediaRecords;
    private List<LinkRecord> linkRecord;

    public static Paint from(final PaintCreatedEvent paintCreatedEvent) {
        return new Paint(
                paintCreatedEvent.id(),
                paintCreatedEvent.inReplyToPaintId() != null,
                paintCreatedEvent.authorId(),
                paintCreatedEvent.authorUsername(),
                paintCreatedEvent.authorNickname(),
                paintCreatedEvent.authorImagePath(),
                paintCreatedEvent.authorStatus(),
                Optional.ofNullable(paintCreatedEvent.quotePaint()).map(Paint::from).orElse(null),
                paintCreatedEvent.createdAt(),
                paintCreatedEvent.text(),
                paintCreatedEvent.hashtagRecords(),
                paintCreatedEvent.mentionRecords(),
                paintCreatedEvent.taggedUserRecords(),
                paintCreatedEvent.mediaRecords(),
                paintCreatedEvent.linkRecords()
        );
    }
}
