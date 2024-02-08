package org.palette.easeltimelineservice.external;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.palette.dto.event.PaintCreatedEvent;
import org.palette.easeltimelineservice.external.grpc.GrpcSocialClient;
import org.palette.easeltimelineservice.external.kafka.EventConsumer;
import org.palette.grpc.GFollowerIdsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyLong;
import static org.palette.easeltimelineservice.service.RedisKeyConstants.FOLLOWER_PAINT_TIMELINE_PREFIX;
import static org.palette.easeltimelineservice.service.RedisKeyConstants.PAINT_PREFIX;
import static org.palette.easeltimelineservice.util.RedisKeyUtil.constructKey;

@SpringBootTest
@EmbeddedKafka
class EventConsumerTest {
    @MockBean
    private GrpcSocialClient gRPCSocialClient;
    @Autowired
    private EventConsumer eventConsumer;
    @Autowired
    private RedisTemplate<String, Object> redistemplate;

    @BeforeEach
    void setUp() {
        Objects.requireNonNull(redistemplate.keys("*")).forEach(key -> redistemplate.delete(key));
    }

    @Test
    void consumePaintCreatedEvent() {
        given(gRPCSocialClient.getFollowerIds(anyLong()))
                .will(invocation -> GFollowerIdsResponse.newBuilder()
                        .addAllFollowerIds(List.of(100L, 200L, 300L))
                        .build());

        final PaintCreatedEvent paintCreatedEvent = generatePaintCreatedEvent();

        eventConsumer.consumePaintCreatedEvent(paintCreatedEvent);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(redistemplate.opsForList().size(constructKey(FOLLOWER_PAINT_TIMELINE_PREFIX.getKey(), 100L))).isEqualTo(1);
            softAssertions.assertThat(redistemplate.opsForList().size(constructKey(FOLLOWER_PAINT_TIMELINE_PREFIX.getKey(), 200L))).isEqualTo(1);
            softAssertions.assertThat(redistemplate.opsForList().size(constructKey(FOLLOWER_PAINT_TIMELINE_PREFIX.getKey(), 300L))).isEqualTo(1);
        });
        assertThat(redistemplate.opsForValue().get(constructKey(PAINT_PREFIX.getKey(), 1L))).isNotNull();
    }

    private static PaintCreatedEvent generatePaintCreatedEvent() {
        return new PaintCreatedEvent(
                1L,
                false,
                1L,
                "test",
                "test",
                "test",
                "test",
                null,
                null,
                "test",
                null,
                null,
                null,
                null,
                null
        );
    }
}
