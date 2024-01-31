package org.palette.easeltimelineservice.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.palette.easeltimelineservice.service.FollowerPaintMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Objects;

@SpringBootTest
class FollowerPaintMapServiceTest {

    @Autowired
    private FollowerPaintMapService followerPaintMapService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    void setUp() {
        Objects.requireNonNull(redisTemplate.keys("*")).forEach(key -> redisTemplate.delete(key));
    }

    @Test
    void addPaintToFollowersTimeline() {
        final List<Long> followerIds = List.of(1L, 2L, 3L);
        final long paintId = 1L;
        followerPaintMapService.addPaintToFollowersTimeline(followerIds, paintId);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(redisTemplate.opsForList().size("follow_timeline:1")).isEqualTo(1);
            softAssertions.assertThat(redisTemplate.opsForList().size("follow_timeline:2")).isEqualTo(1);
            softAssertions.assertThat(redisTemplate.opsForList().size("follow_timeline:3")).isEqualTo(1);
        });
    }
}
