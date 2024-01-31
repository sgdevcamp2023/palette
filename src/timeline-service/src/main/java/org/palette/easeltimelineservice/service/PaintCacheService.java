package org.palette.easeltimelineservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.easeltimelineservice.dto.PaintResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaintCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void cachePaint(Long paintId, PaintResponse paintResponse) {
        redisTemplate.opsForValue().set("paint:" + paintId, paintResponse);
    }
}
