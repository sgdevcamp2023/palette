package org.palette.easeltimelineservice.service;

public record PaintMetrics(Integer replyCount, Integer repaintCount, Integer likeCount) {

    private static final Integer DEFAULT_COUNT = 0;

    public static PaintMetrics of(Integer replyCount, Integer repaintCount, Integer likeCount) {
        return new PaintMetrics(
                replyCount == null ? DEFAULT_COUNT : replyCount,
                repaintCount == null ? DEFAULT_COUNT : repaintCount,
                likeCount == null ? DEFAULT_COUNT : likeCount
        );
    }
}
