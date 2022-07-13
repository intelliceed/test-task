package com.william.task.domain.event.converter;

import com.william.task.domain.event.db.model.EventEntity;
import com.william.task.domain.event.db.model.ScoreEntity;
import com.william.task.domain.event.model.EventDetailedView;
import com.william.task.domain.event.model.ScoreView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventDetailedViewConverterTest {

    private EventDetailedViewConverter converter = new EventDetailedViewConverter(new ScoreConverter());
    @Test
    void convert() {
        LocalDateTime now = LocalDateTime.now();
        EventEntity source = new EventEntity();
        source.setId(1L);
        source.setTeam1("T1");
        source.setTeam2("T2");
        source.setScore1(3);
        source.setScore2(1);
        ScoreEntity historyItem = new ScoreEntity();
        historyItem.setScore1(3);
        historyItem.setScore2(1);
        historyItem.setScoreTimestamp(now);
        historyItem.setReceivedTimestamp(now.plusMinutes(1));
        source.setScoreHistory(List.of(historyItem));

        EventDetailedView target = converter.convert(source);

        Assertions.assertEquals(1L, target.getId());
        Assertions.assertEquals("T1", target.getTeam1());
        Assertions.assertEquals("T2", target.getTeam2());
        Assertions.assertEquals(3, target.getScore1());
        Assertions.assertEquals(1, target.getScore2());
        List<ScoreView> scoreHistory = target.getScoreHistory();
        Assertions.assertFalse(scoreHistory.isEmpty());
        ScoreView item = scoreHistory.get(0);
        Assertions.assertEquals(3, item.getScore1());
        Assertions.assertEquals(1, item.getScore2());
        Assertions.assertEquals(now, item.getTimestamp());
    }
}