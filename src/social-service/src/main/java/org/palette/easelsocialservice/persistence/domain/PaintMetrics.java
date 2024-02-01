package org.palette.easelsocialservice.persistence.domain;

import lombok.Getter;

@Getter
public class PaintMetrics {
    int replyCount;
    int repaintCount;
    int likeCount;
    boolean like;
    boolean repainted;
    boolean marked;
}
