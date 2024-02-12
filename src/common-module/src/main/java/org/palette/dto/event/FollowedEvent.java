package org.palette.dto.event;

import org.palette.dto.EaselEvent;

public record FollowedEvent(
        Long followingUserId,
        Long followedUserId
) implements EaselEvent {
    @Override
    public String getTopic() {
        return TopicConstant.FOLLOWED.value;
    }
}
