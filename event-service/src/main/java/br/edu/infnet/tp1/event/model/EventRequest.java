package br.edu.infnet.tp1.event.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Map;

public record EventRequest(
        @NotBlank String name,
        @NotBlank String location,
        @NotNull @FutureOrPresent LocalDate date,
        @NotNull @Positive Integer capacity,
        @NotBlank String category,
        Map<String, String> metadata) {
}
