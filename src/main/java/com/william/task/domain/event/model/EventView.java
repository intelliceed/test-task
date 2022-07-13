package com.william.task.domain.event.model;

import lombok.Data;

@Data
public class EventView extends EventInitData {
    private long id;
    private int score1;
    private int score2;

    @Override
    public String toString() {
        return "Event: " + getTeam1() + " vs " + getTeam2() + ": " + score1 + "-" + score2;
    }
}
