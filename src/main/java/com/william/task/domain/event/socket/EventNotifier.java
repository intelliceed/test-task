package com.william.task.domain.event.socket;

import com.william.task.domain.event.model.EventView;
import lombok.SneakyThrows;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import static com.william.task.domain.event.utils.SessionUtils.toMessage;

public interface EventNotifier {

    @SneakyThrows
    default void send(EventView event, WebSocketSession session) {
        if (session.isOpen()) {
            session.sendMessage(new TextMessage(toMessage(event)));
        }
    }

    void notifyEventChange(EventView event);
}
