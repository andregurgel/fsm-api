package dev.andregurgel.fsm_api.commons.exception;

import dev.andregurgel.fsm_api.commons.exception.data_integrity_violation.PgIntegrityInfo;
import dev.andregurgel.fsm_api.commons.exception.data_integrity_violation.PgIntegrityViolationResolver;
import dev.andregurgel.fsm_api.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageService messageService;

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException ex, HttpServletRequest req) {
        ErrorResponse body = ErrorResponse.builder()
                .message(messageService.get("missing.required.parameter.exception", ex.getParameterName()))
                .status(HttpStatus.BAD_REQUEST.value())
                .path(req.getRequestURI())
                .timestamp(OffsetDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest req
    ) {
        PgIntegrityInfo info = PgIntegrityViolationResolver.resolve(ex);

        if (info.uniqueViolation()) {
            String messageKey = mapUniqueConstraintToMessageKey(info.constraintName());
            ErrorResponse body = ErrorResponse.builder()
                    .message(messageService.get(messageKey))
                    .status(HttpStatus.CONFLICT.value())
                    .path(req.getRequestURI())
                    .timestamp(OffsetDateTime.now())
                    .build();

            return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
        }

        ErrorResponse body = ErrorResponse.builder()
                .message(messageService.get("error.conflict.integrity"))
                .status(HttpStatus.CONFLICT.value())
                .path(req.getRequestURI())
                .timestamp(OffsetDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(
            ApplicationException ex,
            HttpServletRequest req
    ) {
        ErrorResponse body = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(req.getRequestURI())
                .timestamp(OffsetDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    private String mapUniqueConstraintToMessageKey(String constraintName) {
        if (constraintName == null || constraintName.isBlank()) {
            return "error.conflict.unique";
        }

        return switch (constraintName) {
            case "user_email_uk" -> "error.conflict.unique.user_email";
            case "group_invite_hash_uk" -> "error.conflict.unique.group_invite_hash";
            default -> "error.conflict.unique";
        };
    }
}
