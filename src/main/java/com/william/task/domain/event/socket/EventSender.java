package com.william.task.domain.event.socket;

import com.william.task.domain.event.model.EventView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventSender {

    private final List<EventNotifier> notifiers;

    public EventSender(AllEventsSocketHandler allEventsSocketHandler,
                       EventSocketHandler eventSocketHandler) {
        notifiers = List.of(allEventsSocketHandler, eventSocketHandler);
    }

    public void send(EventView event){
        notifiers.forEach(notifier -> notifier.notifyEventChange(event));
    }
}
