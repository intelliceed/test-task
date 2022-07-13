package com.william.task.domain.event.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.william.task.domain.event.model.EventView;
import lombok.SneakyThrows;
import org.springframework.web.socket.WebSocketSession;

import java.util.Arrays;
import java.util.List;

public class SessionUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static long extractId(WebSocketSession session) {
        List<String> items = Arrays.asList(session.getUri().getPath().split("/"));
        String lastParameter = items.get(items.size() - 1);
        return Long.parseLong(lastParameter);
    }

    @SneakyThrows
    public static String toMessage(EventView event) {
        return objectMapper.writeValueAsString(event);
    }
}
