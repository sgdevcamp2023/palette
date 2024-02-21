package org.palette.easelauthservice.redis;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.palette.easelauthservice.component.generator.emailauth.AuthPayloadGenerator;
import org.palette.exception.BaseException;
import org.palette.exception.ExceptionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@RedisHash(value = "email_auth", timeToLive = 600)
public class EmailAuth {

    @Id
    private String email;

    private String authPayload;

    private Integer threshold;

    public EmailAuth updateAuthPayload() {
        this.authPayload = AuthPayloadGenerator.execute();
        return this;
    }

    public EmailAuth decreaseThreshold() {
        this.threshold -= 1;
        return this;
    }

    public void isAbusing() {
        if (this.getThreshold() == 0) {
            throw new BaseException(ExceptionType.AUTH_400_000003);
        }
    }

    public boolean comparePayload(
            String requestedPayload
    ) {
        return this.authPayload.equals(requestedPayload);
    }
}
