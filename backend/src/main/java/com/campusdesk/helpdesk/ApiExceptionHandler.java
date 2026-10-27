package com.campusdesk.helpdesk;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Turns errors into the responses described in docs/05-api-specification.md. */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<Map<String, String>> notFound(TicketNotFoundException e) {
        return build(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(InvalidTransitionException.class)
    public ResponseEntity<Map<String, String>> conflict(InvalidTransitionException e) {
        return build(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Map<String, String>> invalidInput(InvalidInputException e) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> invalidBody(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return build(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> unreadableBody(HttpMessageNotReadableException e) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, "Request body is missing or has an invalid value");
    }

    private ResponseEntity<Map<String, String>> build(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of("detail", message));
    }
}
