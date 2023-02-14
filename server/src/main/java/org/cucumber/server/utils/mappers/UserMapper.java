package org.cucumber.server.utils.mappers;

import org.cucumber.common.dto.UserDTO;
import org.cucumber.server.models.bo.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "avatar", target = "avatar")
    UserDTO userToUserDTO(User user);

}
