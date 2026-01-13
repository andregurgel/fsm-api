package dev.andregurgel.fsm_api.controller.dto;

import dev.andregurgel.fsm_api.model.enums.TypeEnum;

public record VehiclePatchRecord(
        String description,
        TypeEnum type,
        String plate
) {
}
