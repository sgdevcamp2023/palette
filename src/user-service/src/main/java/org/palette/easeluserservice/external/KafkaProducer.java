package org.palette.easeluserservice.external;

import lombok.RequiredArgsConstructor;
import org.palette.aop.InternalErrorLogging;
import org.palette.dto.EaselEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, EaselEvent> kafkaTemplate;

    @InternalErrorLogging
    @Async
    public void execute(EaselEvent event) {
        kafkaTemplate.send(event.getTopic(), event);
    }
}
