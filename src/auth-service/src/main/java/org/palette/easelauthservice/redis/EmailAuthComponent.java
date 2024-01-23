package org.palette.easelauthservice.redis;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuthComponent implements Serializable {
    private Long userId;
    private String authPayload;
    private Boolean isAuthed;
}
