package br.edu.infnet.tp1.reservation.service;

import br.edu.infnet.tp1.reservation.client.EventCatalogClient;
import br.edu.infnet.tp1.reservation.client.EventSummary;
import br.edu.infnet.tp1.reservation.model.Reservation;
import br.edu.infnet.tp1.reservation.model.ReservationRequest;
import br.edu.infnet.tp1.reservation.model.ReservationResponse;
import br.edu.infnet.tp1.reservation.model.ReservationStatus;
import br.edu.infnet.tp1.reservation.repository.ReservationRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    private final ReservationRepository repository;
    private final EventCatalogClient eventCatalogClient;

    public ReservationService(ReservationRepository repository, EventCatalogClient eventCatalogClient) {
        this.repository = repository;
        this.eventCatalogClient = eventCatalogClient;
    }

    public List<ReservationResponse> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse findById(Long id) {
        return repository.findById(id)
                .map(ReservationResponse::from)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    public EventSummary checkEvent(String eventId) {
        return eventCatalogClient.findById(eventId);
    }

    @Transactional
    public ReservationResponse create(ReservationRequest request) {
        EventSummary event = eventCatalogClient.findById(request.eventId());
        ReservationStatus status = ReservationStatus.CONFIRMED;
        String eventName = event.name();

        if (event.isUnavailable()) {
            status = ReservationStatus.PENDING_VALIDATION;
            eventName = "Evento pendente de validacao";
        } else {
            validateCapacity(event, request.quantity());
        }

        Reservation reservation = new Reservation(
                request.eventId(),
                eventName,
                request.customerName(),
                request.customerEmail(),
                request.quantity(),
                status);

        return ReservationResponse.from(repository.save(reservation));
    }

    private void validateCapacity(EventSummary event, Integer requestedQuantity) {
        long confirmedQuantity = repository.sumQuantityByEventIdAndStatus(
                event.id(),
                ReservationStatus.CONFIRMED);
        int capacity = event.capacity() == null ? 0 : event.capacity();

        if (confirmedQuantity + requestedQuantity > capacity) {
            throw new CapacityExceededException("Event capacity exceeded for event: " + event.id());
        }
    }
}
