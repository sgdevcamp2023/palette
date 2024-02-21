package org.palette.easelnotificationservice.dto.message;

import com.google.firebase.messaging.Message;
import lombok.AccessLevel;
import lombok.Builder;
import org.palette.easelnotificationservice.dto.MessageDto;

@Builder(access = AccessLevel.PRIVATE)
public record NotificationPushMessage(
        Long targetUserId,
        String androidFcmToken,
        Message message
) implements MessageDto {
    @Override
    public MessageDto getMessage() {
        return this;
    }

    public static NotificationPushMessage build(
            Long targetUserId,
            String androidFcmToken,
            Message message
    ) {
        return NotificationPushMessage
                .builder()
                .targetUserId(targetUserId)
                .androidFcmToken(androidFcmToken)
                .message(message)
                .build();
    }
}
