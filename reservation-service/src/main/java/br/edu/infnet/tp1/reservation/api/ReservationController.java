package br.edu.infnet.tp1.reservation.api;

import br.edu.infnet.tp1.reservation.client.EventSummary;
import br.edu.infnet.tp1.reservation.model.ReservationRequest;
import br.edu.infnet.tp1.reservation.model.ReservationResponse;
import br.edu.infnet.tp1.reservation.service.ReservationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReservationResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ReservationResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/events/{eventId}")
    public EventSummary checkEvent(@PathVariable String eventId) {
        return service.checkEvent(eventId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse create(@Valid @RequestBody ReservationRequest request) {
        return service.create(request);
    }
}
