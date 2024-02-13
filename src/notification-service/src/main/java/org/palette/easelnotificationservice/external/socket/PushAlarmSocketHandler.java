package org.palette.easelnotificationservice.external.socket;

import lombok.RequiredArgsConstructor;
import org.palette.easelnotificationservice.persistence.User;
import org.palette.easelnotificationservice.persistence.enums.AlarmAllowedType;
import org.palette.easelnotificationservice.service.UserService;
import org.palette.exception.BaseException;
import org.palette.exception.ExceptionType;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class PushAlarmSocketHandler extends TextWebSocketHandler {

    private final UserService userService;
    private final List<Map<WebSocketSession, User>> sessions = new ArrayList<>();

    @Override
    public void handleTextMessage(
            final WebSocketSession session,
            final TextMessage message
    ) {
    }

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) {
        sessions.add(
                new HashMap<>() {{
                    put(session, userService.loadById(getUserIdAtSessionParameter(session)));
                }}
        );
    }

    @Override
    public void afterConnectionClosed(
            final WebSocketSession session,
            final CloseStatus status
    ) {
        sessions.remove(session);
    }

    public void broadcast(
            final TextMessage message,
            final AlarmAllowedType alarmAllowedType,
            final Long publisherId
    ) {
        final List<WebSocketSession> targetSessions = getSessionsWithAllowedAlarm(
                publisherId,
                alarmAllowedType
        );
        for (final WebSocketSession targetSession : targetSessions) {
            try {
                targetSession.sendMessage(message);
            } catch (IOException e) {
                throw new BaseException(ExceptionType.NOTIFICATION_500_000002);
            }
        }
    }

    private List<WebSocketSession> getSessionsWithAllowedAlarm(
            final Long publisherId,
            final AlarmAllowedType alarmAllowedType
    ) {
        final List<WebSocketSession> results = new ArrayList<>();
        for (final Map<WebSocketSession, User> sessionMap : sessions) {
            for (final Map.Entry<WebSocketSession, User> entry : sessionMap.entrySet()) {
                User user = entry.getValue();
                if (publisherId.equals(user.getId())) break;
                if (user.getAlarmAllowedStatus().contains(alarmAllowedType)) {
                    WebSocketSession wss = entry.getKey();
                    results.add(wss);
                    break;
                }
            }
        }
        return results;
    }

    private Long getUserIdAtSessionParameter(final WebSocketSession session) {
        final Collection<Object> values = session.getAttributes().values();
        final Object firstValue = values.stream().findFirst().orElse(null);
        if (firstValue instanceof Long) {
            return (Long) firstValue;
        } else {
            throw new IllegalArgumentException("Collection values must contain at least one Long value");
        }
    }
}
