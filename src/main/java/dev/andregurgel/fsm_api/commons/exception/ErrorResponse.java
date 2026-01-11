package dev.andregurgel.fsm_api.commons.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse (
        String message,
        Integer status,
        String path,
        OffsetDateTime timestamp,

        List<FieldErrorItem> fields
) {
    public record FieldErrorItem (
            String field,
            String reason
    ) { }
}
