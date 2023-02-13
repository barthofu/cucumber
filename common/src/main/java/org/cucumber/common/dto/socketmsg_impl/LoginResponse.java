package org.cucumber.common.dto.socketmsg_impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.cucumber.common.dto.SocketMessageContent;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponse extends SocketMessageContent {
    private boolean status;
}
