package org.palette.easeltimelineservice.service;

import java.util.Map;

public record PaintMetrics(Integer replyCount, Integer repaintCount, Integer likeCount) {

    private static final Integer DEFAULT_COUNT = 0;
    public static final PaintMetrics DEFAULT_METRICS = new PaintMetrics(DEFAULT_COUNT, DEFAULT_COUNT, DEFAULT_COUNT);

    public static PaintMetrics of(Integer replyCount, Integer repaintCount, Integer likeCount) {
        return new PaintMetrics(
                replyCount == null ? DEFAULT_COUNT : replyCount,
                repaintCount == null ? DEFAULT_COUNT : repaintCount,
                likeCount == null ? DEFAULT_COUNT : likeCount
        );
    }


    public Map<String, String> asMap() {
        return Map.of(
                "replyCount", String.valueOf(replyCount),
                "repaintCount", String.valueOf(repaintCount),
                "likeCount", String.valueOf(likeCount)
        );
    }
}
