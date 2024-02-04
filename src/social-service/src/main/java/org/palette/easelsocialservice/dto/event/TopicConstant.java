package org.palette.easelsocialservice.dto.event;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TopicConstant {

    PAINT_CREATED("paint_created"),
    RE_PAINT_CREATED("re_paint_created"),
    PAINT_LIKE_CREATED("paint_like_created"),
    PAINT_QUOTE_CREATED("paint_quote_created"),

    ;


    final String value;
}
