package dev.andregurgel.fsm_api.controller.filter;

import dev.andregurgel.fsm_api.model.Vehicle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleFilter extends Vehicle {
    private Long groupId;
}
