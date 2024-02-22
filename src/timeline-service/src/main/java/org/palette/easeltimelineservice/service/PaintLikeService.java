package org.palette.easeltimelineservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.easeltimelineservice.util.RedisKeyUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.palette.easeltimelineservice.service.RedisKeyConstants.LIKED_PAINT_PREFIX;


@Service
@RequiredArgsConstructor
public class PaintLikeService {

    private final RedisTemplate<String, String> redistemplate;

    public boolean isLiked(Long userId, Long paintId) {
        String key = RedisKeyUtil.constructKey(LIKED_PAINT_PREFIX.getKey(), paintId);
        return Optional.ofNullable(redistemplate.opsForSet().isMember(key, userId.toString())).orElse(false);
    }

    public void like(Long userId, Long paintId) {
        String key = RedisKeyUtil.constructKey(LIKED_PAINT_PREFIX.getKey(), paintId);
        redistemplate.opsForSet().add(key, userId.toString());
    }

    public void unlike(Long userId, Long paintId) {
        String key = RedisKeyUtil.constructKey(LIKED_PAINT_PREFIX.getKey(), paintId);
        redistemplate.opsForSet().remove(key, userId.toString());
    }
}
