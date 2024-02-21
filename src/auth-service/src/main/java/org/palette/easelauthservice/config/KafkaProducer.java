package org.palette.easelauthservice.config;

import lombok.RequiredArgsConstructor;
import org.palette.dto.EaselEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, EaselEvent> kafkaTemplate;

    @Async
    public void execute(EaselEvent event) {
        kafkaTemplate.send(event.getTopic(), event);
    }
}
