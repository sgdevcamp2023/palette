package org.palette.easeltimelineservice;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventConsumer {
    private final TimelineUsecase timelineUseCase;

    @KafkaListener(topics = "paint-created", groupId = "${spring.kafka.consumer.group-id}")
    public void consumePaintCreatedEvent(PaintCreatedEvent paintCreatedEvent) {
        timelineUseCase.handlePaintCreatedEvent(paintCreatedEvent);
    }
}
