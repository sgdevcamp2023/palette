package org.palette.easeltimelineservice.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisKeyConstants {
    FOLLOWER_PAINT_TIMELINE_PREFIX("follow_timeline"),
    PAINT_PREFIX("paint"),
    METRICS_PREFIX("metrics"),
    LIKED_PAINT_PREFIX("liked_paint");

    private final String key;
}
