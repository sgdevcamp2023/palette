package org.palette.easelsocialservice.external.kafka;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.palette.dto.event.UpdateUserEvent;
import org.palette.easelsocialservice.usecase.UserUsecase;

@ExtendWith(MockitoExtension.class)
class EventConsumerTest {

    @Mock
    private UserUsecase userUsecase;

    @InjectMocks
    private EventConsumer eventConsumer;

    @Test
    void testConsumePaintCreatedEvent() {
        UpdateUserEvent event = new UpdateUserEvent(123L, "won", "hello", "/path/to/image", "/back/image", "http://hello.com");

        eventConsumer.consumePaintCreatedEvent(event);

        verify(userUsecase, times(1)).updateUserImagePath(event.userId(), event.nickname(), event.profileImagePath());
    }
}
