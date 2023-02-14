package org.cucumber.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.cucumber.common.dto.base.SocketMessageContent;

@Getter
@Setter
@RequiredArgsConstructor
public class UsersDTO extends SocketMessageContent {

    private final int totalUsers;
}
