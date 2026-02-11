package dev.andregurgel.fsm_api.controller.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SupplyFilter {
    private Long groupId;
    private Long vehicleId;
    private Long userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
