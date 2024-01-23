package org.palette.easelauthservice.redis;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EmailAuthComponent implements Serializable {
    private Long userId;
    private String authPayload;
    private Boolean isAuthed;
}
