package com.william.task.domain.event.converter;

import com.william.task.domain.event.db.model.ScoreEntity;
import com.william.task.domain.event.model.ScoreView;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScoreConverter {

    public ScoreView convert(ScoreEntity entity) {
        ScoreView view = new ScoreView();
        view.setScore1(entity.getScore1());
        view.setScore2(entity.getScore2());
        view.setTimestamp(entity.getScoreTimestamp());
        return view;
    }

    public ScoreEntity convert(ScoreView view) {
        ScoreEntity entity = new ScoreEntity();
        entity.setScore1(view.getScore1());
        entity.setScore2(view.getScore2());
        entity.setScoreTimestamp(view.getTimestamp());
        entity.setReceivedTimestamp(LocalDateTime.now());
        return entity;
    }
}
