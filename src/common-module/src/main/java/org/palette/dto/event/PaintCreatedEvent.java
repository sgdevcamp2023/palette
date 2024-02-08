package org.palette.dto.event;

import lombok.AccessLevel;
import lombok.Builder;
import org.palette.dto.EaselEvent;
import org.palette.dto.event.detail.HashtagRecord;
import org.palette.dto.event.detail.LinkRecord;
import org.palette.dto.event.detail.MediaRecord;
import org.palette.dto.event.detail.UserRecord;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record PaintCreatedEvent(
        Long writerId,
        Long paintId,
        String paintContent,
        List<HashtagRecord> hashtagRecords,
        List<MediaRecord> mediaRecords,
        List<LinkRecord> linkRecords,
        List<UserRecord> userRecords

) implements EaselEvent {

    @Override
    public String getTopic() {
        return TopicConstant.PAINT_CREATED.value;
    }
}
