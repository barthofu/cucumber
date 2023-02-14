package org.cucumber.server.utils.mappers;

import org.cucumber.common.dto.UserDTO;
import org.cucumber.server.models.bo.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "avatar", target = "avatar")
    @Named("toDTO")
    UserDTO userToUserDTO(User user);

    @IterableMapping(qualifiedByName = "toDTO")
    Set<UserDTO> userSetToUserDTOSet(Set<User> user);
}
