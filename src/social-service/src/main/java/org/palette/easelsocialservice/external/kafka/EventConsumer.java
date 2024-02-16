package org.palette.easelsocialservice.external.kafka;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.UpdateUserImagePathEvent;
import org.palette.easelsocialservice.usecase.UserUsecase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventConsumer {
    private final UserUsecase userUsecase;

    @KafkaListener(topics = "update_profile_image_path", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumePaintCreatedEvent(UpdateUserImagePathEvent updateUserImagePathEvent) {
        userUsecase.updateUserImagePath(updateUserImagePathEvent.userId(), updateUserImagePathEvent.imagePath());
    }
}
