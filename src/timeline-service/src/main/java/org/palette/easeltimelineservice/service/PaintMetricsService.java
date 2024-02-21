package org.palette.easeltimelineservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.easeltimelineservice.util.RedisKeyUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaintMetricsService {

    public static final String REPLY_COUNT = "replyCount";
    public static final String REPAINT_COUNT = "repaintCount";
    public static final String LIKE_COUNT = "likeCount";
    public static final int DELTA = 1;

    private final RedisTemplate<String, String> redistemplate;

    public void incrementReplyCount(final Long pid) {
        changeMetricCount(pid, REPLY_COUNT, DELTA);
    }

    public void incrementRepaintCount(final Long pid) {
        changeMetricCount(pid, REPAINT_COUNT, DELTA);
    }

    public void incrementLikeCount(final Long pid) {
        changeMetricCount(pid, LIKE_COUNT, DELTA);
    }

    public void decrementReplyCount(final Long pid) {
        changeMetricCount(pid, REPLY_COUNT, -DELTA);
    }

    public void decrementRepaintCount(final Long pid) {
        changeMetricCount(pid, REPAINT_COUNT, -DELTA);
    }

    public void decrementLikeCount(final Long pid) {
        changeMetricCount(pid, LIKE_COUNT, -DELTA);
    }

    private void changeMetricCount(final Long pid, final String metric, final int delta) {
        redistemplate.opsForHash()
                .increment(
                        RedisKeyUtil.constructKey(RedisKeyConstants.METRICS_PREFIX.getKey(), pid),
                        metric,
                        delta
                );
    }

    public PaintMetrics getPaintMetrics(final Long pid) {
        final Integer replyCount = (Integer) redistemplate.opsForHash().get(
                RedisKeyUtil.constructKey(RedisKeyConstants.METRICS_PREFIX.getKey(), pid),
                REPLY_COUNT
        );

        final Integer repaintCount = (Integer) redistemplate.opsForHash().get(
                RedisKeyUtil.constructKey(RedisKeyConstants.METRICS_PREFIX.getKey(), pid),
                REPAINT_COUNT
        );

        final Integer likeCount = (Integer) redistemplate.opsForHash().get(
                RedisKeyUtil.constructKey(RedisKeyConstants.METRICS_PREFIX.getKey(), pid),
                LIKE_COUNT
        );
        return new PaintMetrics(replyCount, repaintCount, likeCount);
    }

    public Integer getPaintMetric(final Long pid, final String metric) {
        return (Integer) redistemplate.opsForHash().get(
                RedisKeyUtil.constructKey(RedisKeyConstants.METRICS_PREFIX.getKey(), pid),
                metric
        );
    }
}
