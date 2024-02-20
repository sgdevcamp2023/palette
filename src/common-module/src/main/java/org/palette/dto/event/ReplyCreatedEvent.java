package org.palette.dto.event;

import org.palette.dto.EaselEvent;

public record ReplyCreatedEvent(Long pid) implements EaselEvent {
    @Override
    public String getTopic() {
        return TopicConstant.REPLY_CREATED.value;
    }
}
