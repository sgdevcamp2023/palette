package org.palette.dto.event;

import lombok.Getter;
import org.palette.dto.EaselEvent;

@Getter
public record TemporaryUserDeletionEvent(String email) implements EaselEvent {

    @Override
    public String getTopic() {
        return "temporary_user_deletion";
    }
}
