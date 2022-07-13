package com.william.task.domain.event.model;

import lombok.Data;

import java.util.List;

@Data
public class EventDetailedView extends EventView {
    private List<ScoreView> scoreHistory;

}
