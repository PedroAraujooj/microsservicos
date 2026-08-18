package br.edu.infnet.tp1.reservation.model;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        String eventId,
        String eventName,
        String customerName,
        String customerEmail,
        Integer quantity,
        ReservationStatus status,
        LocalDateTime createdAt) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getEventId(),
                reservation.getEventName(),
                reservation.getCustomerName(),
                reservation.getCustomerEmail(),
                reservation.getQuantity(),
                reservation.getStatus(),
                reservation.getCreatedAt());
    }
}
