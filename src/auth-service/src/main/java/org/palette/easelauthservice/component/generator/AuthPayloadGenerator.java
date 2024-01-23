package org.palette.easelauthservice.component.generator;

import lombok.RequiredArgsConstructor;
import org.palette.easelauthservice.config.ApplicationConfig;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthPayloadGenerator {

    public String execute() {
        StringBuilder payload = new StringBuilder();

        for (int threshod = 0; threshod < 6; threshod++) {
            payload.append(ApplicationConfig.random().nextInt(10));
        }

        return payload.toString();
    }
}
