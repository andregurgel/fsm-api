package dev.andregurgel.fsm_api.service.mapper;

import dev.andregurgel.fsm_api.controller.dto.GroupPatchRecord;
import dev.andregurgel.fsm_api.model.Group;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface GroupMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "owner", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "users", ignore = true),
            @Mapping(target = "vehicles", ignore = true),
    })
    void patch(GroupPatchRecord dto, @MappingTarget Group group);
}
