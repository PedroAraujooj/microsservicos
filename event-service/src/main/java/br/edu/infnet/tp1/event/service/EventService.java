package br.edu.infnet.tp1.event.service;

import br.edu.infnet.tp1.event.model.Event;
import br.edu.infnet.tp1.event.model.EventRequest;
import br.edu.infnet.tp1.event.model.EventResponse;
import br.edu.infnet.tp1.event.repository.EventRepository;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public List<EventResponse> findAll(String category) {
        List<Event> events = category == null || category.isBlank()
                ? repository.findAll()
                : repository.findByCategoryIgnoreCase(category);

        return events.stream()
                .map(EventResponse::from)
                .toList();
    }

    public EventResponse findById(String id) {
        return EventResponse.from(findEvent(id));
    }

    public EventResponse create(EventRequest request) {
        Event event = new Event();
        apply(request, event);
        return EventResponse.from(repository.save(event));
    }

    public EventResponse update(String id, EventRequest request) {
        Event event = findEvent(id);
        apply(request, event);
        return EventResponse.from(repository.save(event));
    }

    public void delete(String id) {
        Event event = findEvent(id);
        repository.delete(event);
    }

    private Event findEvent(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    private void apply(EventRequest request, Event event) {
        event.setName(request.name());
        event.setLocation(request.location());
        event.setDate(request.date());
        event.setCapacity(request.capacity());
        event.setCategory(request.category());
        event.setMetadata(request.metadata() == null ? new LinkedHashMap<>() : request.metadata());
    }
}
