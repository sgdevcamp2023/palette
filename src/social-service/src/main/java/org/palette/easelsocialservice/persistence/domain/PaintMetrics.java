package org.palette.easelsocialservice.persistence.domain;

public record PaintMetrics(
        int replyCount,
        int repaintCount,
        int likeCount,
        boolean like,
        boolean repainted,
        boolean marked
) {
}
