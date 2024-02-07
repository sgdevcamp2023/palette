package org.palette.easelnotificationservice.external.socket;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PushAlarmMessageConst {

    PAINT_CREATED_BY_FOLLOWING_USER("팔로잉하는 {}님의 새로운 페인트가 등록되었습니다."),

    ;

    final String value;

    public String getValue(String nickname) {
        return value.replace("{}", nickname);
    }
}
