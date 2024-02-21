package org.palette.easeltimelineservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final RedisTemplate<String, String> redistemplate;
    private final ObjectMapper objectMapper;

    public void cachePaint(Long paintId, Paint paint) {
        String key = RedisKeyUtil.constructKey(PAINT_PREFIX.getKey(), paintId);
        String paintJson;
        try {
            paintJson = objectMapper.writeValueAsString(paint);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        redistemplate.opsForValue().set(key, paintJson);
        redistemplate.opsForSet().add("paint_keys", paintId.toString());
    }

    public List<Paint> getPaints(final List<Long> paintIds) {
        final List<String> keys = RedisKeyUtil.constructKeys(PAINT_PREFIX.getKey(), paintIds);
        final List<String> value = redistemplate.opsForValue().multiGet(keys);
        return Optional.ofNullable(value)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(json -> {
                    try {
                        return objectMapper.readValue(json, Paint.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    public List<Paint> getRandomPaints() {
        List<Long> paintIds = Optional.ofNullable(redistemplate.opsForSet().randomMembers("paint_keys", 200))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(Long::valueOf)
                .distinct()
                .toList();
        return getPaints(paintIds);
    }
}
