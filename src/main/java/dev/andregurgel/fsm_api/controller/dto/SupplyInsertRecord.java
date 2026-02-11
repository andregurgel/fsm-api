package dev.andregurgel.fsm_api.controller.dto;

import dev.andregurgel.fsm_api.model.enums.SupplyTypeEnum;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SupplyInsertRecord(
        @NotNull BigDecimal liter,
        @NotNull BigDecimal price,
        Integer currentMileage,
        @NotNull SupplyTypeEnum type,
        @NotNull LocalDateTime suppliedAt,
        @NotNull Long vehicleId,
        Long userId // Optional: if null, use current user
) {
}
