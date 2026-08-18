package br.edu.infnet.tp1.reservation.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReservationRequest(
        @NotBlank String eventId,
        @NotBlank String customerName,
        @NotBlank @Email String customerEmail,
        @NotNull @Positive Integer quantity) {
}
