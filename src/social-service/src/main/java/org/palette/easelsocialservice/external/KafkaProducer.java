package org.palette.easelsocialservice.external;

import lombok.RequiredArgsConstructor;
import org.palette.easelsocialservice.dto.event.EaselEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, EaselEvent> kafkaTemplate;

    public void execute(EaselEvent event) {
        kafkaTemplate.send(event.getTopic(), event);
    }
}
