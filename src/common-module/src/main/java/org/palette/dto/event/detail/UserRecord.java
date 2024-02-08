package org.palette.dto.event.detail;

public record UserRecord(
        Long id,
        String username,
        String nickname,
        String imagePath,
        String status
) {
}
