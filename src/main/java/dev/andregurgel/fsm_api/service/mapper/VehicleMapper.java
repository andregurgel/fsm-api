package dev.andregurgel.fsm_api.service.mapper;

import dev.andregurgel.fsm_api.controller.dto.VehiclePatchRecord;
import dev.andregurgel.fsm_api.model.Vehicle;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "group", ignore = true),
    })
    void patch(VehiclePatchRecord dto, @MappingTarget Vehicle vehicle);
}
