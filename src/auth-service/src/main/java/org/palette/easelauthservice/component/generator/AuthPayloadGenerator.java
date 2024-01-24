package org.palette.easelauthservice.component.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class AuthPayloadGenerator {

    private final SecureRandom random;

    public String execute() {
        StringBuilder payload = new StringBuilder();

        for (int iterationCount = 0; iterationCount < 6; iterationCount++) {
            payload.append(random.nextInt(10));
        }

        return payload.toString();
    }
}
