package org.palette.easelauthservice.redis;

import lombok.RequiredArgsConstructor;
import org.palette.easelauthservice.exception.BaseException;
import org.palette.easelauthservice.exception.ExceptionType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, EmailAuthComponent> redisTemplate;
    private static final int AUTH_PAYLOAD_TIMEOUT_HOURS = 1;

    public void createAuthInformation(
            Long userId,
            String authPayload
    ) {
        ValueOperations<String, EmailAuthComponent> valueOperation = redisTemplate.opsForValue();
        buildValueOperation(userId, authPayload, valueOperation);
    }

    public void verifyByRequestUserAndPayload(
            String userId,
            String authPayload
    ) {
        EmailAuthComponent emailAuthComponent = redisTemplate
                .opsForValue()
                .get(userId);

        isExpired(emailAuthComponent);

        if (comparePayloadWithRequest(emailAuthComponent.getAuthPayload(), authPayload)) {
            redisTemplate.delete(userId);
        }
    }

    private void buildValueOperation(
            Long userId,
            String authPayload,
            ValueOperations<String, EmailAuthComponent> valueOperation
    ) {
        valueOperation.set(
                userId.toString(),
                new EmailAuthComponent(
                        userId,
                        authPayload,
                        false
                ),
                AUTH_PAYLOAD_TIMEOUT_HOURS,
                TimeUnit.HOURS
        );
    }

    private boolean comparePayloadWithRequest(
            String authPayload,
            String requestedPayload
    ) {
        return authPayload.equals(requestedPayload);
    }

    private void isExpired(EmailAuthComponent emailAuthComponent) {
        if (emailAuthComponent == null) throw new BaseException(ExceptionType.AUTH_403_000002);
    }
}
