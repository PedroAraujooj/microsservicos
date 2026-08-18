package br.edu.infnet.tp1.reservation.client;

import br.edu.infnet.tp1.reservation.service.EventNotFoundException;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class EventCatalogClient {

    private final EventServiceFeignClient eventServiceFeignClient;

    public EventCatalogClient(EventServiceFeignClient eventServiceFeignClient) {
        this.eventServiceFeignClient = eventServiceFeignClient;
    }

    @CircuitBreaker(name = "eventCatalog", fallbackMethod = "fallbackFindById")
    public EventSummary findById(String eventId) {
        EventSummary event = eventServiceFeignClient.findById(eventId);
        return event == null ? EventSummary.unavailable(eventId) : event;
    }

    private EventSummary fallbackFindById(String eventId, Throwable cause) {
        if (isEventNotFound(cause)) {
            throw new EventNotFoundException(eventId);
        }

        return EventSummary.unavailable(eventId);
    }

    private boolean isEventNotFound(Throwable cause) {
        Throwable current = cause;
        while (current != null) {
            if (current instanceof EventNotFoundException || current instanceof FeignException.NotFound) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }
}
