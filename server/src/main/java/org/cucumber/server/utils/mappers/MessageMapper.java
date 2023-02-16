package org.cucumber.server.utils.mappers;

import org.cucumber.common.dto.MessageDTO;
import org.cucumber.server.models.bo.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {

    @Mapping(source = "content", target = "content")
    @Mapping(source = "from.id", target = "from")
    @Mapping(source = "to.id", target = "to")
    @Mapping(source = "createdAt", target = "date")
    MessageDTO messageToMessageDTO(Message message);

}
