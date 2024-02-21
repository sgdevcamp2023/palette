package org.palette.easelauthservice.redis;

import lombok.RequiredArgsConstructor;
import org.palette.exception.BaseException;
import org.palette.exception.ExceptionType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisEmailAuthService {

    private final EmailAuthRedisRepository repository;
    private static final int DEFAULT_AUTH_ATTEMPT_THRESHOLD = 10;

    public EmailAuth loadByEmail(String email) {
        return repository.findById(email)
                .orElseThrow(() -> new BaseException(ExceptionType.AUTH_404_000001));
    }

    public void create(
            String email,
            String authPayload
    ) {
        EmailAuth authComponent = new EmailAuth(
                email,
                authPayload,
                DEFAULT_AUTH_ATTEMPT_THRESHOLD
        );
        repository.save(authComponent);
    }

    public void update(EmailAuth emailAuth) {
        repository.delete(emailAuth);
        EmailAuth newEmailAuth = new EmailAuth(
                emailAuth.getEmail(),
                emailAuth.getAuthPayload(),
                emailAuth.getThreshold()
        );
        repository.save(newEmailAuth);
    }

    public void delete(EmailAuth emailAuth) {
        repository.delete(emailAuth);
    }
}
