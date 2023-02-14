package org.cucumber.common.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.cucumber.common.dto.base.SocketMessageContent;

@AllArgsConstructor
@Getter
@Setter
public class LoginRequest extends SocketMessageContent {
    private String username;
    private String password;
}
