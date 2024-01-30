package org.palette.easeltimelineservice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaintCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void cachePaint(Long paintId, Paint paint) {
        redisTemplate.opsForValue().set("paint:" + paintId, paint);
    }
}
