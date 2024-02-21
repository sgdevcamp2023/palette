package org.palette.easelauthservice.redis.event;

import lombok.RequiredArgsConstructor;
import org.palette.dto.event.TemporaryUserDeletionEvent;
import org.palette.easelauthservice.config.KafkaProducer;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class EmailAuthExpirationListener implements ApplicationListener<RedisKeyExpiredEvent<EmailAuthExpiredEvent>> {

    private final KafkaProducer kafkaProducer;

    @Override
    public void onApplicationEvent(RedisKeyExpiredEvent<EmailAuthExpiredEvent> event) {
        final String email = new String(event.getId(), StandardCharsets.UTF_8);
        final TemporaryUserDeletionEvent temporaryUserDeletionEvent = new TemporaryUserDeletionEvent(email);
        kafkaProducer.execute(temporaryUserDeletionEvent);
    }
}
