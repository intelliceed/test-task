package com.william.task.domain.event.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScoreView {
    private int score1;
    private int score2;
    private LocalDateTime timestamp;
}
