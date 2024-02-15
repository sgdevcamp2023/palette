package org.palette.dto.event;

import org.palette.dto.EaselEvent;

public record UpdateUserImagePathEvent (
    Long userId,
    String imagePath
) implements EaselEvent {
    @Override
    public String getTopic() {
        return TopicConstant.UPDATE_PROFILE_IMAGE_PATH.value();
    }
}
