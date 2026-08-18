package br.edu.infnet.tp1.reservation.api;

import java.time.Instant;

public record ProblemResponse(int status, String error, String message, Instant timestamp) {
}
