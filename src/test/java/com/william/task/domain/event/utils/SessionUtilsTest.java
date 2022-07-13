package com.william.task.domain.event.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.william.task.domain.event.model.EventView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.socket.WebSocketSession;

import java.net.URI;

class SessionUtilsTest {

    @Test
    void extractId() {
        WebSocketSession session = Mockito.mock(WebSocketSession.class);
        Mockito.when(session.getUri()).thenReturn(URI.create("ws://localhost:8080/events/123"));
        Assertions.assertEquals(123L, SessionUtils.extractId(session));

        Mockito.when(session.getUri()).thenReturn(URI.create("ws://localhost:8080/events/qwe"));
        Assertions.assertThrows(NumberFormatException.class, () -> SessionUtils.extractId(session));
    }

    @Test
    void toMessage() throws JsonProcessingException {
        EventView event = new EventView();
        event.setId(1L);
        event.setTeam1("T1");
        event.setTeam2("T2");
        event.setScore1(1);
        event.setScore2(0);
        String result = SessionUtils.toMessage(event);
        Assertions.assertEquals("{\"team1\":\"T1\",\"team2\":\"T2\",\"id\":1,\"score1\":1,\"score2\":0}",
                result);

        Assertions.assertEquals(new ObjectMapper().readValue(result, EventView.class), event);
    }
}