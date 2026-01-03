package dev.andregurgel.fsm_api.service.mapper;

import dev.andregurgel.fsm_api.controller.dto.UserPatchRecord;
import dev.andregurgel.fsm_api.model.User;
import org.mapstruct.*;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    void patch(UserPatchRecord dto, @MappingTarget User user);
}
