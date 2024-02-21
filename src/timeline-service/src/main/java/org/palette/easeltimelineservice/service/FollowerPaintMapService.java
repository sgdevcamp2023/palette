package org.palette.easeltimelineservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.easeltimelineservice.util.RedisKeyUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.palette.easeltimelineservice.service.RedisKeyConstants.FOLLOWER_PAINT_TIMELINE_PREFIX;

@Service
@RequiredArgsConstructor
public class FollowerPaintMapService {

    private final RedisTemplate<String, String> redistemplate;

    public void addPaintToFollowersTimeline(List<Long> followerIds, Long paintId) {
        followerIds.forEach(userId -> {
            final String key = RedisKeyUtil.constructKey(FOLLOWER_PAINT_TIMELINE_PREFIX.getKey(), userId);
            redistemplate.opsForList()
                    .leftPush(key, paintId.toString());
            redistemplate.expire(key, Duration.ofDays(5));
        });
    }

    public List<Long> getFollowingTimelinePaintIds(final Long userId) {
        final String key = RedisKeyUtil.constructKey(FOLLOWER_PAINT_TIMELINE_PREFIX.getKey(), userId);
        return Optional.ofNullable(redistemplate.opsForList().range(key, 1, -1))
                .orElse(List.of())
                .stream()
                .map(Long::valueOf)
                .toList();
    }
}
