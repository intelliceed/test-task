package com.william.task.domain.event.controller;

import com.william.task.domain.event.model.EventDetailedView;
import com.william.task.domain.event.model.EventInitData;
import com.william.task.domain.event.model.EventView;
import com.william.task.domain.event.model.ScoreView;
import com.william.task.domain.event.service.EventManagementService;
import com.william.task.domain.event.socket.EventSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/event")
@RequiredArgsConstructor
public class EventController {

    private final EventManagementService managementService;
    private final EventSender eventSender;

    @GetMapping
    public List<EventView> loadAllEvents(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return managementService.loadAllEvents(page, size);
    }

    @GetMapping("/{id}")
    public EventDetailedView loadEvent(@PathVariable long id) {
        return managementService.loadEvent(id);
    }

    @PostMapping
    public EventView saveEvent(@RequestBody EventInitData initData) {
        EventView savedEvent = managementService.save(initData);
        eventSender.send(savedEvent);
        return savedEvent;
    }

    @PatchMapping("/{id}")
    public EventView updateEvent(@PathVariable long id,
                                 @RequestBody ScoreView score) {
        EventView savedEvent = managementService.save(id, score);
        eventSender.send(savedEvent);
        return savedEvent;
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable long id) {
        managementService.delete(id);
    }
}
