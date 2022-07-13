package com.william.task.domain.event.utils;

import com.william.task.domain.event.db.model.EventEntity;
import com.william.task.domain.event.db.model.ScoreEntity;
import com.william.task.domain.event.model.ScoreView;

import java.util.function.Predicate;

public class Predicates {

    public static Predicate<ScoreEntity> isSameScorePredicate(ScoreView score) {
        return exists ->
                exists.getScore1() == score.getScore1() &&
                exists.getScore2() == score.getScore2();
    }

    public static Predicate<EventEntity> isScoreUpgradePredicate(ScoreView score) {
        return event ->
                (score.getScore1() > event.getScore1() && score.getScore2() >= event.getScore2()) ||
                (score.getScore1() >= event.getScore1() && score.getScore2() > event.getScore2());

    }
}
