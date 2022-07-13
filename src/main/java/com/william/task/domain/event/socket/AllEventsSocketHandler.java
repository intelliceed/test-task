package com.william.task.domain.event.socket;

import com.william.task.domain.event.model.EventView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AllEventsSocketHandler extends TextWebSocketHandler implements EventNotifier {

    private Map<String, WebSocketSession> sessions = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getId());
    }

    @Override
    public void notifyEventChange(EventView event) {
        sessions.forEach((s, session) -> send(event, session));
    }
}
