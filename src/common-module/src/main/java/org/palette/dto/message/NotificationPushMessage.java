package org.palette.dto.message;

import org.palette.dto.EaselMessage;

public record NotificationPushMessage(
        Long targetUserId,
        String pushTitle,
        String pushContent

) implements EaselMessage {
    @Override
    public EaselMessage getMessage() {
        return this;
    }
}
