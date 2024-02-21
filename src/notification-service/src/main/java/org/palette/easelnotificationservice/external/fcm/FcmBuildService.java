package org.palette.easelnotificationservice.external.fcm;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.palette.easelnotificationservice.dto.message.NotificationPushMessage;
import org.palette.easelnotificationservice.external.queue.RabbitMessageQueueService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmBuildService {

    private final RabbitMessageQueueService rabbitMessageQueueService;

    public void buildFcmAndPushToMessageQueue(
            Long targetUserId,
            String androidFcmToken,
            String title,
            String body
    ) {
        final Message message = Message.builder()
                .setToken(androidFcmToken)
                .setTopic(FcmConst.FCM_TOPIC)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();

        final NotificationPushMessage notificationPushMessage = NotificationPushMessage.build(
                targetUserId,
                androidFcmToken,
                message
        );

        rabbitMessageQueueService.pushMessage(notificationPushMessage);
    }
}
