package com.rps.flora.service.mapper;

import com.rps.flora.domain.FloraEmployee;
import com.rps.flora.domain.User;
import com.rps.flora.service.dto.FloraEmployeeDTO;
import com.rps.flora.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FloraEmployee} and its DTO {@link FloraEmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface FloraEmployeeMapper extends EntityMapper<FloraEmployeeDTO, FloraEmployee> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userId")
    FloraEmployeeDTO toDto(FloraEmployee s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
