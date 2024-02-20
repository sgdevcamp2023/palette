package org.palette.easelauthservice.redis.event;

import lombok.Getter;
import org.palette.easelauthservice.redis.EmailAuth;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailAuthExpiredEvent extends ApplicationEvent {

    private final EmailAuth emailAuth;

    public EmailAuthExpiredEvent(EmailAuth emailAuth) {
        super(emailAuth);
        this.emailAuth = emailAuth;
    }
}
