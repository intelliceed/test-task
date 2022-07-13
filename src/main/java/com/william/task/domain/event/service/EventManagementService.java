package com.william.task.domain.event.service;

import com.william.task.domain.event.converter.EventConverter;
import com.william.task.domain.event.converter.EventDetailedViewConverter;
import com.william.task.domain.event.converter.ScoreConverter;
import com.william.task.domain.event.db.model.EventEntity;
import com.william.task.domain.event.db.model.ScoreEntity;
import com.william.task.domain.event.db.repository.EventRepository;
import com.william.task.domain.event.model.EventDetailedView;
import com.william.task.domain.event.model.EventInitData;
import com.william.task.domain.event.model.EventView;
import com.william.task.domain.event.model.ScoreView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.william.task.domain.event.utils.Predicates.isSameScorePredicate;
import static com.william.task.domain.event.utils.Predicates.isScoreUpgradePredicate;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventManagementService {

    private final EventRepository eventRepository;
    private final EventConverter eventConverter;
    private final ScoreConverter scoreConverter;
    private final EventDetailedViewConverter detailedViewConverter;

    public EventDetailedView loadEvent(long id) {
        return eventRepository.findById(id)
                .map(detailedViewConverter::convert)
                .orElseThrow(failNoEvent(id));
    }

    public List<EventView> loadAllEvents(int page, int size) {
        return eventRepository.findAll(PageRequest.of(page, size)).stream()
                .map(eventConverter::convert)
                .collect(Collectors.toList());
    }

    public EventView save(EventInitData event) {
        return save(eventConverter.update(new EventEntity(), event));
    }

    public EventView save(long id, ScoreView score) {
        EventEntity event = eventRepository.findById(id).orElseThrow(failNoEvent(id));
        addScore(event, score);
        updateScoreIfUpgrade(event, score);
        return save(event);
    }

    private void addScore(EventEntity event, ScoreView score) {
        event.getScoreHistory().stream().filter(isSameScorePredicate(score))
                .findFirst()
                .ifPresentOrElse(scoreEntity -> {
                            log.info("Same score: {} already exists. Will be skipped", score);
                        },
                        () -> {
                            ScoreEntity scoreEntity = scoreConverter.convert(score);
                            scoreEntity.setEvent(event);
                            event.getScoreHistory().add(scoreEntity);
                        });
    }

    private void updateScoreIfUpgrade(EventEntity event, ScoreView score) {
        if (isScoreUpgradePredicate(score).test(event)) {
            event.setScore1(score.getScore1());
            event.setScore2(score.getScore2());
        }
    }

    private EventView save(EventEntity EventEntity) {
        return eventConverter.convert(eventRepository.save(EventEntity));
    }

    public void delete(long id) {
        eventRepository.deleteById(id);
    }

    private Supplier<IllegalArgumentException> failNoEvent(long id) {
        return () -> new IllegalArgumentException("No event found with id: " + id);
    }
}
