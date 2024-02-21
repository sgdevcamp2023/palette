package org.palette.easelnotificationservice.external.queue;

import lombok.RequiredArgsConstructor;
import org.palette.easelnotificationservice.dto.MessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMessageQueueService {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void pushMessage(final MessageDto messageDto) {
        rabbitTemplate.convertAndSend(
                exchangeName,
                routingKey,
                messageDto
        );
    }
}
