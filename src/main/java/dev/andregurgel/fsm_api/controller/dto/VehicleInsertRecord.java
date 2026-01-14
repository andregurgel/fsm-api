package dev.andregurgel.fsm_api.controller.dto;

import dev.andregurgel.fsm_api.model.enums.VehicleTypeEnum;
import jakarta.validation.constraints.NotNull;

public record VehicleInsertRecord(
        @NotNull Long groupId,
        @NotNull String description,
        @NotNull VehicleTypeEnum type,
        String plate
) {
}
