package org.palette.easeltimelineservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.easeltimelineservice.util.RedisKeyUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaintMetricsService {

    public static final String REPLY_COUNT = "replyCount";
    public static final String REPAINT_COUNT = "repaintCount";
    public static final String LIKE_COUNT = "likeCount";

    public static final String VIEW_COUNT = "viewCount";
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

    public void incrementViewCount(final Long aLong) {
        changeMetricCount(aLong, VIEW_COUNT, DELTA);
    }

    private void changeMetricCount(final Long pid, final String metric, final int delta) {
        String key = RedisKeyUtil.constructKey(RedisKeyConstants.METRICS_PREFIX.getKey(), metric, pid);
        redistemplate.opsForValue().increment(key, delta);
    }

    public PaintMetrics getPaintMetrics(final Long pid) {
        final Integer replyCount = getPaintMetric(pid, REPLY_COUNT);
        final Integer repaintCount = getPaintMetric(pid, REPAINT_COUNT);
        final Integer likeCount = getPaintMetric(pid, LIKE_COUNT);
        final Integer viewCount = getPaintMetric(pid, VIEW_COUNT);
        return PaintMetrics.of(replyCount, repaintCount, likeCount, viewCount);
    }

    public Integer getPaintMetric(final Long pid, final String metric) {
        final String string = Optional.ofNullable(redistemplate.opsForHash().get(
                RedisKeyUtil.constructKey(RedisKeyConstants.METRICS_PREFIX.getKey(), pid),
                metric
        )).orElse("0").toString();
        return Integer.parseInt(string);
    }

    public void create(final Long id) {
        redistemplate.opsForHash().putAll(
                RedisKeyUtil.constructKey(RedisKeyConstants.METRICS_PREFIX.getKey(), id),
                PaintMetrics.DEFAULT_METRICS.asMap()
        );
    }
}
