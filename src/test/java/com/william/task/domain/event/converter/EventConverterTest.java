package com.william.task.domain.event.converter;

import com.william.task.domain.event.db.model.EventEntity;
import com.william.task.domain.event.model.EventInitData;
import com.william.task.domain.event.model.EventView;
import com.william.task.domain.event.model.ScoreView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventConverterTest {

    private EventConverter converter = new EventConverter();

    @Test
    void convert() {
        EventEntity source = new EventEntity();
        source.setId(1L);
        source.setTeam1("T1");
        source.setTeam2("T2");
        source.setScore1(3);
        source.setScore2(1);
        EventView target = converter.convert(source);

        Assertions.assertEquals(source.getId(), target.getId());
        Assertions.assertEquals(source.getTeam1(), target.getTeam1());
        Assertions.assertEquals(source.getTeam2(), target.getTeam2());
        Assertions.assertEquals(source.getScore1(), target.getScore1());
        Assertions.assertEquals(source.getScore2(), target.getScore2());
    }

    @Test
    void updateFromScore() {
        EventEntity target = new EventEntity();
        ScoreView source = new ScoreView();
        source.setScore1(3);
        source.setScore2(1);
        target = converter.update(target, source);

        Assertions.assertEquals(3, target.getScore1());
        Assertions.assertEquals(1, target.getScore2());
    }

    @Test
    void updateFromInit() {
        EventEntity target = new EventEntity();
        EventInitData source = new EventInitData();
        source.setTeam1("T1");
        source.setTeam2("T2");
        target = converter.update(target, source);

        Assertions.assertEquals("T1", target.getTeam1());
        Assertions.assertEquals("T2", target.getTeam2());
    }
}