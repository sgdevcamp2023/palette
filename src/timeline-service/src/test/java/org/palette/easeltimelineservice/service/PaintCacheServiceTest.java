package org.palette.easeltimelineservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.palette.easeltimelineservice.dto.PaintResponse;
import org.palette.easeltimelineservice.service.PaintCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/*
@Service
@RequiredArgsConstructor
public class PaintCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void cachePaint(Long paintId, Paint paint) {
        redisTemplate.opsForValue().set("paint:" + paintId, paint);
    }
}
 */
@SpringBootTest
class PaintCacheServiceTest {

    @Autowired
    private PaintCacheService paintCacheService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    void setUp() {
        Objects.requireNonNull(redisTemplate.keys("*")).forEach(key -> redisTemplate.delete(key));
    }
    @Test
    void cachePaint() {
        // given
        final Long paintId = 1L;
        final PaintResponse paintResponse = new PaintResponse(
                1L,
                false,
                1L,
                "test",
                "test",
                "test",
                "test",
                null,
                "test",
                0,
                0,
                0,
                false,
                false,
                false,
                0,
                null,
                null
        );
        // when
        paintCacheService.cachePaint(paintId, paintResponse);

        // then
        final PaintResponse cachedPaintResponse = (PaintResponse) redisTemplate.opsForValue().get("paint:" + paintId);
        assertEquals(paintResponse, cachedPaintResponse);
    }
}
