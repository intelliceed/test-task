package com.william.task.domain.event.converter;

import com.william.task.domain.event.db.model.ScoreEntity;
import com.william.task.domain.event.model.ScoreView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ScoreConverterTest {

    private ScoreConverter converter = new ScoreConverter();

    @Test
    void convertFromEntity() {
        LocalDateTime now = LocalDateTime.now();
        ScoreEntity source = new ScoreEntity();
        source.setScore1(3);
        source.setScore2(1);
        source.setScoreTimestamp(now);
        source.setReceivedTimestamp(now.plusMinutes(1));
        ScoreView target = converter.convert(source);

        Assertions.assertEquals(3, target.getScore1());
        Assertions.assertEquals(1, target.getScore2());
        Assertions.assertEquals(now, target.getTimestamp());
    }

    @Test
    void convertFromView() {
        LocalDateTime now = LocalDateTime.now();
        ScoreView source = new ScoreView();
        source.setScore1(3);
        source.setScore2(1);
        source.setTimestamp(now);
        ScoreEntity target = converter.convert(source);

        Assertions.assertEquals(3, target.getScore1());
        Assertions.assertEquals(1, target.getScore2());
        Assertions.assertEquals(now, target.getScoreTimestamp());
        Assertions.assertNotNull(target.getReceivedTimestamp());
    }
}