package org.palette.easelnotificationservice.config;

import lombok.RequiredArgsConstructor;
import org.palette.easelnotificationservice.external.socket.PushAlarmSocketHandler;
import org.palette.easelnotificationservice.external.socket.SocketHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final PushAlarmSocketHandler pushAlarmSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/websocket")
                .addInterceptors(new SocketHandshakeInterceptor());
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return pushAlarmSocketHandler;
    }
}
