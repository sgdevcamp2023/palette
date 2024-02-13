package org.palette.easelsearchservice.external;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.PaintCreatedEvent;
import org.palette.easelsearchservice.usecase.SearchUsecase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventConsumer {
    private final SearchUsecase searchUsecase;

    @KafkaListener(topics = "paint_created", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumePaintCreatedEvent(PaintCreatedEvent paintCreatedEvent) {
        searchUsecase.paintCreated(paintCreatedEvent);
    }
}
