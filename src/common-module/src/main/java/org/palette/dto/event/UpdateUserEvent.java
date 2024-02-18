package org.palette.dto.event;

import org.palette.dto.EaselEvent;

public record UpdateUserEvent (
    Long userId,
    String nickname,
    String introduce,
    String profileImagePath,
    String backgroundImagePath,
    String websitePath
) implements EaselEvent {
    @Override
    public String getTopic() {
        return TopicConstant.UPDATE_USER.value();
    }
}
