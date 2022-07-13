package com.william.task.configuration;

import com.william.task.domain.event.socket.AllEventsSocketHandler;
import com.william.task.domain.event.socket.EventSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class TaskApplicationConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(allEventsSocketHandler(), "/events")
                .addHandler(eventSocketHandler(), "/events/{id}");
    }

    @Bean
    public EventSocketHandler eventSocketHandler() {
        return new EventSocketHandler();
    }

    @Bean
    public AllEventsSocketHandler allEventsSocketHandler() {
        return new AllEventsSocketHandler();
    }


}