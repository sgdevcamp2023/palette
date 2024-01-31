package org.palette.easeltimelineservice.external;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.palette.easeltimelineservice.dto.PaintCreatedEvent;
import org.palette.easeltimelineservice.dto.PaintResponse;
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

@SpringBootTest
@EmbeddedKafka
class EventConsumerTest {
    @MockBean
    private GrpcSocialClient gRPCSocialClient;
    @Autowired
    private EventConsumer eventConsumer;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    void setUp() {
        Objects.requireNonNull(redisTemplate.keys("*")).forEach(key -> redisTemplate.delete(key));
    }

    @Test
    void consumePaintCreatedEvent() {
        given(gRPCSocialClient.getFollowerIds(anyLong()))
                .will(invocation -> GFollowerIdsResponse.newBuilder()
                        .addAllFollowerIds(List.of(1L, 2L, 3L))
                        .build());

        final Long userId = 1L;
        final PaintCreatedEvent paintCreatedEvent = generatePaintCreatedEvent(userId);

        eventConsumer.consumePaintCreatedEvent(paintCreatedEvent);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(redisTemplate.opsForList().size("follow_timeline:1")).isEqualTo(1);
            softAssertions.assertThat(redisTemplate.opsForList().size("follow_timeline:2")).isEqualTo(1);
            softAssertions.assertThat(redisTemplate.opsForList().size("follow_timeline:3")).isEqualTo(1);
        });
        assertThat(redisTemplate.opsForValue().get("paint:1")).isEqualTo(paintCreatedEvent.paintResponse());
    }

    private static PaintCreatedEvent generatePaintCreatedEvent(final Long userId) {
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
        return new PaintCreatedEvent(userId, paintId, paintResponse);
    }
}
