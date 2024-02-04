package org.palette.easelsocialservice.dto.event.details;

import lombok.AccessLevel;
import lombok.Builder;
import org.palette.easelsocialservice.dto.event.EaselEvent;
import org.palette.easelsocialservice.persistence.domain.Paint;

@Builder(access = AccessLevel.PRIVATE)
public record PaintCreatedEvent(
        Long writerId,
        Long paintId,
        String paintContent,
        String paintHashtag
) implements EaselEvent {

    public static PaintCreatedEvent build(
            Long userId, Paint paint
    ) {
        return PaintCreatedEvent.builder()
                .writerId(userId)
                .paintId(paint.getPid())
                .paintContent(paint.getContent())
                .paintHashtag(paint.getHashtags().toString())
                .build();
    }

    @Override
    public String getTopic() {
        return TopicConstant.PAINT_CREATED.value;
    }
}
