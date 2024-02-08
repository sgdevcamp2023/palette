package org.palette.easeltimelineservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.easeltimelineservice.util.RedisKeyUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.palette.easeltimelineservice.service.RedisKeyConstants.FOLLOWER_PAINT_TIMELINE_PREFIX;

@Service
@RequiredArgsConstructor
public class FollowerPaintMapService {

    private final RedisTemplate<String, Object> redistemplate;

    public void addPaintToFollowersTimeline(List<Long> followerIds, Long paintId) {
        followerIds.forEach(userId -> {
            final String key = RedisKeyUtil.constructKey(FOLLOWER_PAINT_TIMELINE_PREFIX.getKey(), userId);
            redistemplate.opsForList()
                    .leftPush(key, paintId);
            redistemplate.expire(key, Duration.ofDays(5));
        });
    }

    public List<Long> getFollowingTimelinePaintIds(final Long userId, final Pageable pageable) {
        final String key = RedisKeyUtil.constructKey(FOLLOWER_PAINT_TIMELINE_PREFIX.getKey(), userId);
        return Optional.ofNullable(redistemplate.opsForList()
                        .range(key, pageable.getOffset(), pageable.getOffset() + pageable.getPageSize() - 1))
                .orElse(List.of())
                .stream()
                .map(object -> Long.valueOf(object.toString()))
                .toList();
    }
}
