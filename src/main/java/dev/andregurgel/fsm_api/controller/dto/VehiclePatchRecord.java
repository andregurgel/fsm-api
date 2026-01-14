package dev.andregurgel.fsm_api.controller.dto;

import dev.andregurgel.fsm_api.model.enums.VehicleTypeEnum;

public record VehiclePatchRecord(
        String description,
        VehicleTypeEnum type,
        String plate
) {
}
