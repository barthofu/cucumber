package org.cucumber.common.dto.socketmsg_impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.cucumber.common.dto.SocketMessageContent;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class HelloMsg extends SocketMessageContent {
    private String text;
}
