package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@DynamicInsert
@Embeddable
public record StaticContentPath(
        @Column(name = "profile_image_path", nullable = false)
        String profileImagePath,
        @Column(name = "background_image_path", nullable = false)
        String backgroundImagePath,
        @Column(name = "website_path", nullable = false)
        String websitePath
) {
}
