package org.cucumber.common.dto.generics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.cucumber.common.dto.UserDTO;
import org.cucumber.common.dto.base.SocketMessageContent;

@Getter
@Setter
@AllArgsConstructor
public class ClientTarget extends SocketMessageContent {
    private UserDTO target;
}
