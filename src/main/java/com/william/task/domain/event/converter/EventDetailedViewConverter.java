package com.william.task.domain.event.converter;

import com.william.task.domain.event.model.EventDetailedView;
import com.william.task.domain.event.db.model.EventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventDetailedViewConverter {

    private final ScoreConverter scoreConverter;

    public EventDetailedView convert(EventEntity entity) {
        EventDetailedView view = new EventDetailedView();
        view.setId(entity.getId());
        view.setTeam1(entity.getTeam1());
        view.setTeam2(entity.getTeam2());
        view.setScore1(entity.getScore1());
        view.setScore2(entity.getScore2());
        view.setScoreHistory(entity.getScoreHistory().stream()
                .map(scoreConverter::convert)
                .collect(Collectors.toList()));
        return view;
    }
}
