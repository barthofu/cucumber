package org.cucumber.common.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.cucumber.common.dto.base.SocketMessageContent;

@AllArgsConstructor
@Getter
@Setter
public class JoinRoomResponse extends SocketMessageContent {

    private int roomId;
    private int userId;
}
