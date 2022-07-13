package com.william.task.domain.event.utils;

import com.william.task.domain.event.db.model.EventEntity;
import com.william.task.domain.event.db.model.ScoreEntity;
import com.william.task.domain.event.model.ScoreView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PredicatesTest {

    @Test
    void isSameScorePredicate() {
        ScoreView newScore = new ScoreView();
        ScoreEntity oldScore = new ScoreEntity();

        newScore.setScore1(1);
        newScore.setScore2(1);
        oldScore.setScore1(1);
        oldScore.setScore2(1);
        Assertions.assertTrue(Predicates.isSameScorePredicate(newScore).test(oldScore));

        newScore.setScore1(2);
        newScore.setScore2(1);
        oldScore.setScore1(1);
        oldScore.setScore2(1);
        Assertions.assertFalse(Predicates.isSameScorePredicate(newScore).test(oldScore));

        newScore.setScore1(1);
        newScore.setScore2(2);
        oldScore.setScore1(1);
        oldScore.setScore2(1);
        Assertions.assertFalse(Predicates.isSameScorePredicate(newScore).test(oldScore));

        newScore.setScore1(1);
        newScore.setScore2(1);
        oldScore.setScore1(2);
        oldScore.setScore2(1);
        Assertions.assertFalse(Predicates.isSameScorePredicate(newScore).test(oldScore));

        newScore.setScore1(1);
        newScore.setScore2(1);
        oldScore.setScore1(1);
        oldScore.setScore2(2);
        Assertions.assertFalse(Predicates.isSameScorePredicate(newScore).test(oldScore));
    }

    @Test
    void isScoreUpgradePredicate() {
        ScoreView newScore = new ScoreView();
        EventEntity oldScore = new EventEntity();

        newScore.setScore1(1);
        newScore.setScore2(1);
        oldScore.setScore1(1);
        oldScore.setScore2(1);
        Assertions.assertFalse(Predicates.isScoreUpgradePredicate(newScore).test(oldScore));

        newScore.setScore1(1);
        newScore.setScore2(0);
        oldScore.setScore1(1);
        oldScore.setScore2(1);
        Assertions.assertFalse(Predicates.isScoreUpgradePredicate(newScore).test(oldScore));

        newScore.setScore1(0);
        newScore.setScore2(1);
        oldScore.setScore1(1);
        oldScore.setScore2(1);
        Assertions.assertFalse(Predicates.isScoreUpgradePredicate(newScore).test(oldScore));

        newScore.setScore1(1);
        newScore.setScore2(2);
        oldScore.setScore1(1);
        oldScore.setScore2(1);
        Assertions.assertTrue(Predicates.isScoreUpgradePredicate(newScore).test(oldScore));

        newScore.setScore1(2);
        newScore.setScore2(1);
        oldScore.setScore1(1);
        oldScore.setScore2(1);
        Assertions.assertTrue(Predicates.isScoreUpgradePredicate(newScore).test(oldScore));
    }
}