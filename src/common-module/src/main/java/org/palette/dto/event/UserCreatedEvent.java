package org.palette.dto.event;

import org.palette.dto.EaselEvent;

public record UserCreatedEvent(
        Long id,
        String nickname,
        String username,
        String introduce
) implements EaselEvent {
    @Override
    public String getTopic() {
        return TopicConstant.USER_CREATED.value;
    }
}
