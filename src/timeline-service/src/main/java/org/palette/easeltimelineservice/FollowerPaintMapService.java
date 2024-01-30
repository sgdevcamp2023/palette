package org.palette.easeltimelineservice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowerPaintMapService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void addPaintToFollowersTimeline(List<Long> followerIds, Long paintId) {
        followerIds.forEach(userId ->
                redisTemplate.opsForList().leftPush("follow_timeline:" + userId, paintId));
    }
}
