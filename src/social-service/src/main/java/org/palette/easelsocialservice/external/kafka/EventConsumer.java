package org.palette.easelsocialservice.external.kafka;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.UpdateUserEvent;
import org.palette.easelsocialservice.usecase.UserUsecase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventConsumer {
    private final UserUsecase userUsecase;

    @KafkaListener(topics = "update_user", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumePaintCreatedEvent(UpdateUserEvent updateUserEvent) {
        userUsecase.updateUserImagePath(updateUserEvent.userId(), updateUserEvent.nickname(), updateUserEvent.profileImagePath());
    }
}
