package org.palette.easelnotificationservice.external.broker;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.PaintCreatedEvent;
import org.palette.easelnotificationservice.external.grpc.GrpcSocialClient;
import org.palette.easelnotificationservice.external.socket.PushAlarmSocketHandler;
import org.palette.easelnotificationservice.persistence.User;
import org.palette.easelnotificationservice.persistence.enums.AlarmAllowedType;
import org.palette.easelnotificationservice.service.UserService;
import org.palette.grpc.GFollowerIdsResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.util.List;

import static org.palette.easelnotificationservice.external.socket.PushAlarmMessageConst.PAINT_CREATED_BY_FOLLOWING_USER;
import static org.palette.easelnotificationservice.persistence.enums.AlarmAllowedType.PAINT_CREATED;

@Component
@RequiredArgsConstructor
public class KafkaEventConsumer {

    private final GrpcSocialClient grpcSocialClient;
    private final UserService userService;
    private final PushAlarmSocketHandler pushAlarmSocketHandler;

    @KafkaListener(topics = "paint_created", groupId = "${spring.kafka.consumer.group-id}")
    public void consumePaintCreatedEvent(PaintCreatedEvent paintCreatedEvent) {
        final GFollowerIdsResponse paintWriterFollowersIds = grpcSocialClient.getPaintWriterFollowersIds(
                paintCreatedEvent.authorId()
        );
        final List<Long> followerIds = paintWriterFollowersIds.getFollowerIdsList();
        final List<User> followers = userService.loadAllByIds(followerIds);
        final User writer = userService.loadById(paintCreatedEvent.authorId());

        for (User follower : followers) {
            final List<AlarmAllowedType> userAlarmAllowedStatus = follower.getAlarmAllowedStatus();
            if (isUserIgnoredAlarmType(PAINT_CREATED, userAlarmAllowedStatus)) continue;

            pushAlarmSocketHandler.broadcast(
                    new TextMessage(PAINT_CREATED_BY_FOLLOWING_USER.getValue(writer.getNickname())),
                    PAINT_CREATED,
                    writer.getId()
            );
        }
    }

    private boolean isUserIgnoredAlarmType(
            final AlarmAllowedType pushAlarmType,
            final List<AlarmAllowedType> userAlarmAllowedStatus
    ) {
        return !userAlarmAllowedStatus.contains(pushAlarmType);
    }
}
