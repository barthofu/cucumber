package org.cucumber.common.dto.contents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.cucumber.common.dto.SocketMessageContent;

@AllArgsConstructor
@Getter
@Setter
public class JoinRoomResponse extends SocketMessageContent {

    private int roomId;
    private int userId;
}
