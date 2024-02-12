package org.palette.dto.event;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TopicConstant {

    PAINT_CREATED("paint_created"),
    REPAINT_CREATED("repaint_created"),
    LIKED_PAINT("liked_paint"),
    QUOTED_PAINT("quoted_paint"),
    FOLLOWED("followed"),

    ;
    final String value;

    public String value() {
        return value;
    }
}
