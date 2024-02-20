package org.palette.easeltimelineservice.external.kafka;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.PaintCreatedEvent;
import org.palette.dto.event.ReplyCreatedEvent;
import org.palette.easeltimelineservice.usecase.TimelineUsecase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventConsumer {
    private final TimelineUsecase timelineUseCase;

    @KafkaListener(topics = "paint_created", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumePaintCreatedEvent(PaintCreatedEvent paintCreatedEvent) {
        timelineUseCase.handlePaintCreatedEvent(paintCreatedEvent);
    }

    @KafkaListener(topics = "reply_created", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeReplyCreatedEvent(ReplyCreatedEvent replyCreatedEvent) {
        timelineUseCase.handleReplyCreatedEvent(replyCreatedEvent);
    }
}
