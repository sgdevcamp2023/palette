package src.main.java.org.palette.dto.event.detail;

public record MentionRecord(
        Integer start,
        Integer end,
        Long userId,
        String mention
) {
}
