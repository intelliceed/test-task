package com.william.task.domain.event.converter;

import com.william.task.domain.event.db.model.EventEntity;
import com.william.task.domain.event.model.EventInitData;
import com.william.task.domain.event.model.EventView;
import com.william.task.domain.event.model.ScoreView;
import org.springframework.stereotype.Component;

@Component
public class EventConverter {

    public EventView convert(EventEntity entity) {
        EventView view = new EventView();
        view.setId(entity.getId());
        view.setTeam1(entity.getTeam1());
        view.setTeam2(entity.getTeam2());
        view.setScore1(entity.getScore1());
        view.setScore2(entity.getScore2());
        return view;
    }

    public EventEntity update(EventEntity entity, EventInitData view) {
        entity.setTeam1(view.getTeam1());
        entity.setTeam2(view.getTeam2());
        return entity;
    }

    public EventEntity update(EventEntity entity, ScoreView view) {
        entity.setScore1(view.getScore1());
        entity.setScore2(view.getScore2());
        return entity;
    }
}
