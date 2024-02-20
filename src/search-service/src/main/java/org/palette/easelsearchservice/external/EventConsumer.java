package org.palette.easelsearchservice.external;

import lombok.RequiredArgsConstructor;
import org.palette.aop.InternalErrorLogging;
import org.palette.dto.event.PaintCreatedEvent;
import org.palette.dto.event.UpdateUserEvent;
import org.palette.dto.event.UserCreatedEvent;
import org.palette.easelsearchservice.usecase.SearchUsecase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventConsumer {
    private final SearchUsecase searchUsecase;

    @InternalErrorLogging
    @KafkaListener(topics = "paint_created", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumePaintCreatedEvent(PaintCreatedEvent paintCreatedEvent) {
        searchUsecase.paintCreated(paintCreatedEvent);
    }

    @InternalErrorLogging
    @KafkaListener(topics = "user_created", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
        searchUsecase.createUser(userCreatedEvent);
    }

    @InternalErrorLogging
    @KafkaListener(topics = "user_created", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeUserUpdatedEvent(UpdateUserEvent updateUserEvent) {
        searchUsecase.updateUser(updateUserEvent);
    }
}
