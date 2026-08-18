package br.edu.infnet.tp1.event.model;

import java.time.LocalDate;
import java.util.Map;

public record EventResponse(
        String id,
        String name,
        String location,
        LocalDate date,
        Integer capacity,
        String category,
        Map<String, String> metadata) {

    public static EventResponse from(Event event) {
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getLocation(),
                event.getDate(),
                event.getCapacity(),
                event.getCategory(),
                event.getMetadata());
    }
}
