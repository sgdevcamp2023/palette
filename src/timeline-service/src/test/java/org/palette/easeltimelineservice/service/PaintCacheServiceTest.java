package org.palette.easeltimelineservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.palette.easeltimelineservice.persistence.domain.Paint;
import org.palette.easeltimelineservice.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PaintCacheServiceTest {

    @Autowired
    private PaintCacheService paintCacheService;

    @Autowired
    private RedisTemplate<String, Object> redistemplate;

    @BeforeEach
    void setUp() {
        Objects.requireNonNull(redistemplate.keys("*")).forEach(key -> redistemplate.delete(key));
    }

    @Test
    void cachePaint() {
        // given
        final Long paintId = 1L;
        final Paint paint = new Paint(
                1L,
                false,
                1L,
                "authorUsername",
                "authorNickname",
                "authorImagePath",
                "authorStatus",
                null,
                null,
                "text",
                null,
                null,
                null,
                null,
                null);
        // when
        paintCacheService.cachePaint(paintId, paint);

        // then
        PaintResponse cachedPaintResponse = PaintResponse.from((Paint) redistemplate.opsForValue()
                .get(RedisKeyUtil.constructKey(RedisKeyConstants.PAINT_PREFIX.getKey(), paintId)));
        assertThat(cachedPaintResponse).isNotNull();
    }
}
