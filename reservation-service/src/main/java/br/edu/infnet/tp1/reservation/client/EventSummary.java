package br.edu.infnet.tp1.reservation.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

public record EventSummary(
        String id,
        String name,
        String location,
        LocalDate date,
        Integer capacity,
        String category,
        Boolean unavailable) {

    public static EventSummary unavailable(String eventId) {
        return new EventSummary(
                eventId,
                "Evento pendente de validacao",
                null,
                null,
                0,
                null,
                true);
    }

    @JsonIgnore
    public boolean isUnavailable() {
        return Boolean.TRUE.equals(unavailable);
    }
}
