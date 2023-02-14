package org.cucumber.common.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.cucumber.common.dto.UserDTO;
import org.cucumber.common.dto.base.SocketMessageContent;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetFavOfUserResponse extends SocketMessageContent {
    Set<UserDTO> userDTOSet;
}
