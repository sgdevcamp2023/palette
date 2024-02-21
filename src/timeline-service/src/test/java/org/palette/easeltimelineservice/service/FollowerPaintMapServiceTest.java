package org.palette.easeltimelineservice.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Objects;

import static org.palette.easeltimelineservice.service.RedisKeyConstants.FOLLOWER_PAINT_TIMELINE_PREFIX;
import static org.palette.easeltimelineservice.util.RedisKeyUtil.constructKey;

@SpringBootTest
class FollowerPaintMapServiceTest {

    @Autowired
    private FollowerPaintMapService followerPaintMapService;
    @Autowired
    private RedisTemplate<String, Object> redistemplate;

    @BeforeEach
    void setUp() {
        Objects.requireNonNull(redistemplate.keys("*")).forEach(key -> redistemplate.delete(key));
    }

    @Test
    void addPaintToFollowersTimeline() {
        final List<Long> followerIds = List.of(100L, 200L, 300L);
        final long paintId = 1L;
        followerPaintMapService.addPaintToFollowersTimeline(followerIds, paintId);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(redistemplate.opsForList().size(constructKey(FOLLOWER_PAINT_TIMELINE_PREFIX.getKey(), 100L))).isEqualTo(1);
            softAssertions.assertThat(redistemplate.opsForList().size(constructKey(FOLLOWER_PAINT_TIMELINE_PREFIX.getKey(), 200L))).isEqualTo(1);
            softAssertions.assertThat(redistemplate.opsForList().size(constructKey(FOLLOWER_PAINT_TIMELINE_PREFIX.getKey(), 300L))).isEqualTo(1);
        });
    }
}
