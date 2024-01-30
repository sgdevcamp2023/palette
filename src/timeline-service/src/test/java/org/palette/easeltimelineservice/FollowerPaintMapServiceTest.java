package org.palette.easeltimelineservice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(redisTemplate.opsForList().range("follow_timeline:1", 0, -1)).containsExactly(paintId);
    }
}
