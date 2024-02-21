package org.palette.easelnotificationservice.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Deprecated
@ConditionalOnProperty(name = "fcm.deprecated", havingValue = "false")
@Configuration
public class FcmConfig {

    @Value("${fcm.key}")
    private String secretKey;

    @Value("${fcm.project-id}")
    private String projectId;

    @Bean
    public FirebaseApp firebaseApp() {
        final ClassPathResource resource = new ClassPathResource(secretKey);
        try (InputStream stream = resource.getInputStream()) {
            final FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(stream))
                    .setProjectId(projectId)
                    .build();
            return FirebaseApp.initializeApp(firebaseOptions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

