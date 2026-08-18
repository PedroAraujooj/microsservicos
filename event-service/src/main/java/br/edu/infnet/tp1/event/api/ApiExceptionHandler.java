package br.edu.infnet.tp1.event.api;

import br.edu.infnet.tp1.event.service.EventNotFoundException;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ProblemResponse> handleNotFound(EventNotFoundException exception) {
        return problem(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemResponse> handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Invalid request");

        return problem(HttpStatus.BAD_REQUEST, message);
    }

    private ResponseEntity<ProblemResponse> problem(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ProblemResponse(status.value(), status.getReasonPhrase(), message, Instant.now()));
    }
}
