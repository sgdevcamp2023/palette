package org.palette.easeltimelineservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.easeltimelineservice.persistence.domain.Paint;
import org.palette.easeltimelineservice.util.RedisKeyUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.palette.easeltimelineservice.service.RedisKeyConstants.PAINT_PREFIX;

@Service
@RequiredArgsConstructor
public class PaintCacheService {

    private final RedisTemplate<String, Object> redistemplate;

    public void cachePaint(Long paintId, Paint paint) {
        String key = RedisKeyUtil.constructKey(PAINT_PREFIX.getKey(), paintId);
        redistemplate.opsForValue().set(key, paint);
    }

    public List<Paint> getPaints(final List<Long> paintIds) {
        final List<String> keys = RedisKeyUtil.constructKeys(PAINT_PREFIX.getKey(), paintIds);
        return Optional.ofNullable(redistemplate.opsForValue().multiGet(keys))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(Paint.class::cast)
                .toList();
    }

    public List<Paint> getRandomPaints() {
        return Optional.ofNullable(redistemplate.opsForSet().randomMembers(PAINT_PREFIX.getKey(), 200))
                .stream()
                .map(Paint.class::cast)
                .toList();
    }
}
