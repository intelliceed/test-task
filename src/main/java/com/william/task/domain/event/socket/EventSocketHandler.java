package com.william.task.domain.event.socket;

import com.william.task.domain.event.model.EventView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.william.task.domain.event.utils.SessionUtils.extractId;

@Slf4j
public class EventSocketHandler extends TextWebSocketHandler implements EventNotifier {

    private Map<Long, Map<String, WebSocketSession>> sessions = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        long eventId = extractId(session);
        sessions.putIfAbsent(eventId, Collections.synchronizedMap(new HashMap<>()));
        sessions.get(eventId).putIfAbsent(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        long eventId = extractId(session);
        sessions.getOrDefault(eventId, Collections.emptyMap())
                .remove(session.getId());
    }


    @Override
    public void notifyEventChange(EventView event) {
        sessions.getOrDefault(event.getId(), Collections.emptyMap())
                .forEach((s, session) -> send(event, session));
    }
}
