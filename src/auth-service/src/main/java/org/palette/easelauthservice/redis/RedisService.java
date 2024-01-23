package org.palette.easelauthservice.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, EmailAuthComponent> redisTemplate;

    public void createAuthInformation(
            Long userId,
            String authPayload
    ) {
        ValueOperations<String, EmailAuthComponent> valueOperation =
                redisTemplate.opsForValue();

        valueOperation.set(
                userId.toString(),
                new EmailAuthComponent(
                        userId,
                        authPayload,
                        false
                ),
                1,
                TimeUnit.HOURS
        );
    }

    public EmailAuthComponent getUserSession(Long userId) {
        return this.redisTemplate.opsForValue().get(userId.toString());
    }
}
