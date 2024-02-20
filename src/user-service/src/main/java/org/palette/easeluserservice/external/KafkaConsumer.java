package org.palette.easeluserservice.external;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.TemporaryUserDeletionEvent;
import org.palette.easeluserservice.usecase.UserUsecase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final UserUsecase userUsecase;

    @KafkaListener(topics = "paint_created", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumePaintCreatedEvent(TemporaryUserDeletionEvent temporaryUserDeletionEvent) {
        userUsecase.deleteTemporaryUser(temporaryUserDeletionEvent);
    }
}
